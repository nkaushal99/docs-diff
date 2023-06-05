package com.nikhil.service.document.client;


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
public class OldNewFiles implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private byte[] oldFileBytes;
  private String oldFileName;
  private String oldFileContentType;

  private byte[] newFileBytes;
  private String newFileName;
  private String newFileContentType;

  public OldNewFiles(MultipartFile oldFile, MultipartFile newFile) {
    setOldFile(oldFile);
    setNewFile(newFile);
  }

  public void setOldFile(MultipartFile file) {
    try {
      this.oldFileBytes = file.getBytes();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    this.oldFileName = file.getOriginalFilename();
    this.oldFileContentType = file.getContentType();
  }

  public void setNewFile(MultipartFile file) {
    try {
      this.newFileBytes = file.getBytes();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    this.newFileName = file.getOriginalFilename();
    this.newFileContentType = file.getContentType();
  }
}
