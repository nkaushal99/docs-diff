package com.nikhil.service.highlight.client;

import com.nikhil.service.diff.client.DiffList;
import com.nikhil.service.document.client.OldNewFiles;

public class HighlightRequest {

  private OldNewFiles oldNewFiles;

  private DiffList diffList;

  public HighlightRequest(){}

  public HighlightRequest(OldNewFiles oldNewFiles, DiffList diffList) {
    super();
    this.oldNewFiles = oldNewFiles;
    this.diffList = diffList;
  }

  public OldNewFiles getOldNewFiles() {
    return oldNewFiles;
  }

  public void setOldNewFiles(OldNewFiles oldNewFiles) {
    this.oldNewFiles = oldNewFiles;
  }

  public DiffList getDiffList() {
    return diffList;
  }

  public void setDiffList(DiffList diffList) {
    this.diffList = diffList;
  }

}
