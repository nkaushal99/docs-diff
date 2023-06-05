package com.nikhil.service.diff.server.controller;

import com.nikhil.service.diff.client.Diff;
import com.nikhil.service.diff.client.DiffList;
import com.nikhil.service.diff.client.event.DiffEvent;
import com.nikhil.service.diff.server.util.CompareText;
import com.nikhil.service.diff.server.util.TextExtractor;
import com.nikhil.service.document.client.OldNewFiles;
import com.nikhil.service.document.client.event.CompareEvent;
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
import java.util.LinkedList;

@Slf4j
@RestController
public class DiffController {

  private static final String TOPIC = "diff-topic";

  @Autowired
  private KafkaTemplate<String, DiffEvent> kafkaTemplate;

  @KafkaListener(topics = "compare-topic", groupId = "diff-group")
  public void consumeCompareEvent(ConsumerRecord<String, CompareEvent> record) {
    CompareEvent compareEvent = record.value();
    OldNewFiles oldNewFiles = compareEvent.getOldNewFiles();
    DiffList diffList = createDiffs(oldNewFiles);

    // publish event
    DiffEvent diffEvent = new DiffEvent();
    diffEvent.setDiffList(diffList);
    diffEvent.setRequestId(compareEvent.getRequestId());
    kafkaTemplate.send(TOPIC, diffEvent);
  }

  @PostMapping("/diffs")
  public DiffList createDiffs(@RequestBody OldNewFiles oldNewFiles) {
    String oldText, newText;
    try {
      MultipartFile oldFile = MultipartUtils.reconstructMultipartFile(oldNewFiles.getOldFileName(),
        oldNewFiles.getOldFileContentType(), oldNewFiles.getOldFileBytes());
      MultipartFile newFile = MultipartUtils.reconstructMultipartFile(oldNewFiles.getNewFileName(),
        oldNewFiles.getNewFileContentType(), oldNewFiles.getNewFileBytes());
      oldText = new TextExtractor(oldFile).getText();
      newText = new TextExtractor(newFile).getText();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    LinkedList<Diff> diffs = CompareText.compare(oldText, newText);
    return new DiffList(diffs);
  }

}
