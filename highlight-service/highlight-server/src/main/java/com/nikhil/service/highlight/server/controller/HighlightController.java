package com.nikhil.service.highlight.server.controller;

import com.nikhil.service.diff.client.DiffList;
import com.nikhil.service.diff.client.Operation;
import com.nikhil.service.document.client.MultipartFileImpl;
import com.nikhil.service.document.client.OldNewFiles;
import com.nikhil.service.highlight.client.HighlightRequest;
import com.nikhil.service.highlight.client.HighlightResponse;
import com.nikhil.service.highlight.server.util.TextHighlighter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
public class HighlightController {

  @PostMapping("/highlight")
  public HighlightResponse highlight(@RequestBody HighlightRequest highlightRequest)
    throws IOException {
    OldNewFiles oldNewFiles = highlightRequest.getOldNewFiles();
    DiffList diffList = highlightRequest.getDiffList();
    TextHighlighter oldTextHighlighter = null, newTextHighlighter = null;

    try {
      oldTextHighlighter = new TextHighlighter(oldNewFiles.getOldFile(), diffList,
        Operation.DELETE, false);
      newTextHighlighter = new TextHighlighter(oldNewFiles.getNewFile(), diffList,
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
    byte[] bytes = Files.readAllBytes(filePath);
    MultipartFileImpl oldFile = new MultipartFileImpl(fileName, bytes);

    filePath = newTextHighlighter.getHighlightedDocumentPath();
    fileName = filePath.getFileName().toString();
    bytes = Files.readAllBytes(filePath);
    MultipartFileImpl newFile = new MultipartFileImpl(fileName, bytes);
    return new HighlightResponse(oldFile, newFile);
  }
}
