package com.nikhil.service.document.server.controller;

import com.nikhil.service.document.client.OldNewFiles;
import com.nikhil.service.document.client.event.CompareEvent;
import com.nikhil.service.highlight.client.HighlightResponse;
import com.nikhil.service.highlight.client.event.HighlightEvent;
import com.nikhil.shared.util.MultipartUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

@Slf4j
@RestController
@RequestMapping("/v1")
public class DocumentController {

  private static final String TOPIC = "compare-topic";

  private final Map<String, CountDownLatch> requestLatches = new ConcurrentHashMap<>();

  private final Map<String, OldNewFiles> responses = new ConcurrentHashMap<>();

  @Autowired
  private KafkaTemplate<String, CompareEvent> kafkaTemplate;

  @PostMapping("/compare")
  public OldNewFiles compare(@ModelAttribute OldNewFiles oldNewFiles) throws InterruptedException {

    String requestId = UUID.randomUUID().toString(); // Generate a unique identifier for the request

    // Create a new latch for the current request and store it in the map
    CountDownLatch latch = new CountDownLatch(1);
    requestLatches.put(requestId, latch);

    // publish event
    CompareEvent compareEvent = new CompareEvent();
    compareEvent.setOldNewFiles(oldNewFiles);
    compareEvent.setRequestId(requestId); // Include the requestId in the event
    kafkaTemplate.send(TOPIC, compareEvent);

    // Wait for response from HighlightService
    latch.await();

    // Retrieve the response for the current request
    OldNewFiles response = responses.get(requestId);

    // Remove the latch and response from the maps
    requestLatches.remove(requestId);
    responses.remove(requestId);

    // Return the response received from HighlightService
    return response;
  }

  @KafkaListener(topics = "highlight-topic", groupId = "document-group")
  public void consumeHighlightEvent(ConsumerRecord<String, HighlightEvent> record) {
    HighlightEvent highlightEvent = record.value();
    HighlightResponse highlightResponse = highlightEvent.getHighlightResponse();
    MultipartFile oldFileHltd =
      MultipartUtils.reconstructMultipartFile(highlightResponse.getOldFileHltdName(),
        highlightResponse.getOldFileHltdContentType(), highlightResponse.getOldFileHltdBytes());
    MultipartFile newFileHltd =
      MultipartUtils.reconstructMultipartFile(highlightResponse.getNewFileHltdName(),
        highlightResponse.getNewFileHltdContentType(), highlightResponse.getNewFileHltdBytes());
    OldNewFiles response = new OldNewFiles(oldFileHltd, newFileHltd);

    String requestId = highlightEvent.getRequestId(); // Get the requestId from the event

    // Store the response in the map using the requestId as the key
    responses.put(requestId, response);

    // Retrieve the latch for the current request and count it down
    CountDownLatch latch = requestLatches.get(requestId);
    if (latch != null) {
      latch.countDown();
    }
  }
}
