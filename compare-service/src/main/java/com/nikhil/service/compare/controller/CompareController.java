package com.nikhil.service.compare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.nikhil.service.compare.model.DiffList;
import com.nikhil.service.compare.model.HighlightRequest;
import com.nikhil.service.compare.model.OldNewDocuments;

@RestController
@RequestMapping("/compare")
@RefreshScope
public class CompareController {

  @Autowired
  RestTemplate template;

  @Value("${testValue}")
  private String test;

  @PostMapping
  public void compare(@RequestBody OldNewDocuments oldNewDocuments) {
    System.out.println("***************\n" + test + "**************");
    DiffList diffList =
        template.postForObject("http://diff-service/diffs", oldNewDocuments, DiffList.class);

    HighlightRequest highlightRequest = new HighlightRequest(oldNewDocuments, diffList);
    template.postForObject("http://highlight-service/highlight", highlightRequest, Void.class);

  }
}
