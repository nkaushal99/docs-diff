package com.nikhil.service.document.client;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

@JsonSerialize(using = MultipartFileSerializer.class)
@JsonDeserialize(using = MultipartFileDeserializer.class)
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MultipartFileImpl implements MultipartFile {

  private String name;

  private byte[] bytes;

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getOriginalFilename() {
    return name;
  }

  @Override
  public String getContentType() {
    return MediaType.APPLICATION_OCTET_STREAM_VALUE;
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
  public byte[] getBytes() {
    return bytes;
  }

  @Override
  public InputStream getInputStream() {
    return new ByteArrayInputStream(bytes);
  }

  @Override
  public void transferTo(File dest) throws IllegalStateException {}
}
