package com.nikhil.shared;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;

@Setter
@Getter
@NoArgsConstructor
public class CustomMultipartFile implements MultipartFile, Serializable {

  private String filename;
  private String contentType;
  private byte[] bytes;

  public CustomMultipartFile(String filename, String contentType, byte[] bytes) {
    this.filename = filename;
    this.contentType = contentType;
    this.bytes = bytes;
  }

  @Override
  @NonNull
  public String getName() {
    return filename;
  }

  @Override
  public String getOriginalFilename() {
    return filename;
  }

  @Override
  public boolean isEmpty() {
    return bytes.length == 0;
  }

  @Override
  public long getSize() {
    return bytes.length;
  }

  @Override
  @NonNull
  public InputStream getInputStream() {
    return new ByteArrayInputStream(bytes);
  }

  @Override
  public void transferTo(@NonNull File dest) throws IOException, IllegalStateException {
    Files.write(dest.toPath(), bytes);
  }
}
