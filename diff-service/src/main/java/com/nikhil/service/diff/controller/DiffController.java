package com.nikhil.service.diff.controller;

import java.io.IOException;
import java.util.LinkedList;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nikhil.service.diff.model.DiffList;
import com.nikhil.service.diff.model.OldNewDocuments;
import com.nikhil.service.diff.util.CompareText;
import com.nikhil.service.diff.util.DiffTool.Diff;
import com.nikhil.service.diff.util.TextExtractor;


@RestController
public class DiffController {

  @PostMapping("/diffs")
  public DiffList createDiffs(@RequestBody OldNewDocuments oldNewDocuments) {
    String oldText = null, newText = null;
    try {
      oldText = new TextExtractor(oldNewDocuments.getOldFile()).getText();
      newText = new TextExtractor(oldNewDocuments.getNewFile()).getText();
    } catch (IOException e) {
      e.getMessage();
    }
    LinkedList<Diff> diffs = CompareText.compare(oldText, newText);
    DiffList diffList = new DiffList(diffs);
    return diffList;
  }

}
