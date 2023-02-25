package com.nikhil.service.diff.util;

import java.util.LinkedList;

import com.nikhil.service.diff.util.DiffTool.Diff;

public class CompareText {

  public static LinkedList<Diff> compare(String oldText, String newText) {
    DiffTool differ = new DiffTool();
    differ.setDiff_Timeout(120);

    LinkedList<Diff> diffList = differ.diff_main(oldText, newText);
    differ.diff_cleanupSemantic(diffList);
    return diffList;
  }
}
