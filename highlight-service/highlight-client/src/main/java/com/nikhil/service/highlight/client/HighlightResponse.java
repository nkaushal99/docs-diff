package com.nikhil.service.highlight.client;

import com.nikhil.service.document.client.MultipartFileImpl;

import java.io.Serial;
import java.io.Serializable;

public class HighlightResponse implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private MultipartFileImpl oldFileHighlighted;

  private MultipartFileImpl newFileHighlighted;

  public HighlightResponse(){}

  public HighlightResponse(MultipartFileImpl oldFileHighlighted, MultipartFileImpl newFileHighlighted){
    this.oldFileHighlighted = oldFileHighlighted;
    this.newFileHighlighted = newFileHighlighted;
  }

  public MultipartFileImpl getOldFileHighlighted() {
    return oldFileHighlighted;
  }

  public void setOldFileHighlighted(MultipartFileImpl oldFileHighlighted) {
    this.oldFileHighlighted = oldFileHighlighted;
  }

  public MultipartFileImpl getNewFileHighlighted() {
    return newFileHighlighted;
  }

  public void setNewFileHighlighted(MultipartFileImpl newFileHighlighted) {
    this.newFileHighlighted = newFileHighlighted;
  }
}
