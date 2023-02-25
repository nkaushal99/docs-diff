package com.nikhil.service.highlight.model;

public class HighlightRequest {

  private OldNewDocuments oldNewDocuments;

  private DiffList diffList;

  public HighlightRequest(OldNewDocuments oldNewDocuments, DiffList diffList) {
    super();
    this.oldNewDocuments = oldNewDocuments;
    this.diffList = diffList;
  }

  public OldNewDocuments getOldNewDocuments() {
    return oldNewDocuments;
  }

  public void setOldNewDocuments(OldNewDocuments oldNewDocuments) {
    this.oldNewDocuments = oldNewDocuments;
  }

  public DiffList getDiffList() {
    return diffList;
  }

  public void setDiffList(DiffList diffList) {
    this.diffList = diffList;
  }

}
