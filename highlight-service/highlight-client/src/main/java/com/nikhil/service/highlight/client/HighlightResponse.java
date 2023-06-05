package com.nikhil.service.highlight.client;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class HighlightResponse implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  // hltd -> highlighted
  private byte[] oldFileHltdBytes;
  private String oldFileHltdName;
  private String oldFileHltdContentType;

  private byte[] newFileHltdBytes;
  private String newFileHltdName;
  private String newFileHltdContentType;

  public HighlightResponse(MultipartFile oldFileHltd, MultipartFile newFileHltd) {
    setOldFileHltd(oldFileHltd);
    setNewFileHltd(newFileHltd);
  }

  public void setOldFileHltd(MultipartFile file) {
    try {
      this.oldFileHltdBytes = file.getBytes();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    this.oldFileHltdName = file.getOriginalFilename();
    this.oldFileHltdContentType = file.getContentType();
  }

  public void setNewFileHltd(MultipartFile file) {
    try {
      this.newFileHltdBytes = file.getBytes();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    this.newFileHltdName = file.getOriginalFilename();
    this.newFileHltdContentType = file.getContentType();
  }

}
