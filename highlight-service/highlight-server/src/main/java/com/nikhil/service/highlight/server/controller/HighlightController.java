package com.nikhil.service.highlight.server.controller;

import com.nikhil.service.diff.client.DiffList;
import com.nikhil.service.diff.client.Operation;
import com.nikhil.service.diff.client.event.DiffEvent;
import com.nikhil.service.document.client.OldNewFiles;
import com.nikhil.service.document.client.event.CompareEvent;
import com.nikhil.service.highlight.client.HighlightRequest;
import com.nikhil.service.highlight.client.HighlightResponse;
import com.nikhil.service.highlight.client.event.HighlightEvent;
import com.nikhil.service.highlight.server.util.TextHighlighter;
import com.nikhil.shared.SharedConstants;
import com.nikhil.shared.util.MultipartUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

@Slf4j
@RestController
public class HighlightController {

  private static final String TOPIC = "highlight-topic";

  private final Queue<CompareEvent> compareEventQueue = new LinkedBlockingDeque<>();

  private final Queue<DiffEvent> diffEventQueue = new LinkedBlockingDeque<>();

  @Autowired
  private KafkaTemplate<String, HighlightEvent> kafkaTemplate;

  @KafkaListener(topics = "compare-topic", groupId = "highlight-group")
  public void consumeCompareEvent(ConsumerRecord<String, CompareEvent> record) {
    CompareEvent compareEvent = record.value();
    compareEventQueue.add(compareEvent);
    combineEvents();
  }

  @KafkaListener(topics = "diff-topic", groupId = "highlight-group")
  public void consumeDiffEvent(ConsumerRecord<String, DiffEvent> record) {
    DiffEvent diffEvent = record.value();
    diffEventQueue.add(diffEvent);
    combineEvents();
  }

  private void combineEvents() {
    if (!compareEventQueue.isEmpty() && !diffEventQueue.isEmpty()) {
      // Combine the events from DocumentService and DiffService
      // Access the compareEventList and diffEventList to retrieve the respective events
      // Perform required logic with the combined events

      CompareEvent compareEvent = compareEventQueue.remove();
      DiffEvent diffEvent = diffEventQueue.remove();
      if (!compareEvent.getRequestId().equals(diffEvent.getRequestId())) {
        return;
      }

      OldNewFiles oldNewFiles = compareEvent.getOldNewFiles();
      DiffList diffList = diffEvent.getDiffList();

      // publish event
      HighlightRequest highlightRequest = new HighlightRequest(oldNewFiles, diffList);
      HighlightResponse highlightResponse;
      highlightResponse = highlight(highlightRequest);
      HighlightEvent highlightEvent = new HighlightEvent();
      highlightEvent.setHighlightResponse(highlightResponse);
      highlightEvent.setRequestId(compareEvent.getRequestId());
      kafkaTemplate.send(TOPIC, highlightEvent);
    }
  }

  @PostMapping("/highlight")
  public HighlightResponse highlight(@RequestBody HighlightRequest highlightRequest) {
    OldNewFiles oldNewFiles = highlightRequest.getOldNewFiles();
    DiffList diffList = highlightRequest.getDiffList();
    TextHighlighter oldTextHighlighter = null, newTextHighlighter = null;

    MultipartFile oldFile = MultipartUtils.reconstructMultipartFile(oldNewFiles.getOldFileName(),
      oldNewFiles.getOldFileContentType(), oldNewFiles.getOldFileBytes());
    MultipartFile newFile = MultipartUtils.reconstructMultipartFile(oldNewFiles.getNewFileName(),
      oldNewFiles.getNewFileContentType(), oldNewFiles.getNewFileBytes());
    try {
      oldTextHighlighter = new TextHighlighter(oldFile, diffList,
        Operation.DELETE, false);
      newTextHighlighter = new TextHighlighter(newFile, diffList,
        Operation.INSERT, false);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (oldTextHighlighter != null && newTextHighlighter != null) {
        oldTextHighlighter.close();
        newTextHighlighter.close();
      }
    }
    Path filePath = oldTextHighlighter.getHighlightedDocumentPath();
    String fileName = filePath.getFileName().toString();
    byte[] bytes;
    try {
      bytes = Files.readAllBytes(filePath);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    MultipartFile oldFileHltd = MultipartUtils.reconstructMultipartFile(fileName,
      SharedConstants.CONTENT_TYPE_PDF, bytes);

    filePath = newTextHighlighter.getHighlightedDocumentPath();
    fileName = filePath.getFileName().toString();
    try {
      bytes = Files.readAllBytes(filePath);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    MultipartFile newFileHltd = MultipartUtils.reconstructMultipartFile(fileName,
      SharedConstants.CONTENT_TYPE_PDF, bytes);
    return new HighlightResponse(oldFileHltd, newFileHltd);
  }
}
