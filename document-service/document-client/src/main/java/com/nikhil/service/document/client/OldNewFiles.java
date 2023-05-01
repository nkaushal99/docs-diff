package com.nikhil.service.document.client;


import java.io.Serial;
import java.io.Serializable;

public class OldNewFiles implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private MultipartFileImpl oldFile;

  private MultipartFileImpl newFile;

  public OldNewFiles(){}

  public OldNewFiles(MultipartFileImpl oldFile, MultipartFileImpl newFile){
    this.oldFile = oldFile;
    this.newFile = newFile;
  }

  public MultipartFileImpl getOldFile() {
    return oldFile;
  }

  public void setOldFile(MultipartFileImpl oldFile) {
    this.oldFile = oldFile;
  }

  public MultipartFileImpl getNewFile() {
    return newFile;
  }

  public void setNewFile(MultipartFileImpl newFile) {
    this.newFile = newFile;
  }
}
