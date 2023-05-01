package com.nikhil.service.document.server.controller;

import com.nikhil.service.diff.client.DiffList;
import com.nikhil.service.document.client.MultipartFileImpl;
import com.nikhil.service.document.client.OldNewFiles;
import com.nikhil.service.highlight.client.HighlightRequest;
import com.nikhil.service.highlight.client.HighlightResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RefreshScope
@RequestMapping("/v1")
public class DocumentController {

  @Autowired
  RestTemplate template;

  @PostMapping("/compare")
  public OldNewFiles compare(@RequestBody OldNewFiles oldNewFiles) {
    DiffList diffList =
      template.postForObject("http://diff-service/diffs", oldNewFiles, DiffList.class);

    HighlightRequest highlightRequest = new HighlightRequest(oldNewFiles, diffList);
    HighlightResponse highlightResponse =
      template.postForObject("http://highlight-service/highlight", highlightRequest,
        HighlightResponse.class);
    MultipartFileImpl oldFile = highlightResponse.getOldFileHighlighted();
    MultipartFileImpl newFile = highlightResponse.getNewFileHighlighted();

    return new OldNewFiles(oldFile, newFile);
  }
}
