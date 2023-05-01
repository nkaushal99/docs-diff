package com.nikhil.service.document.client;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

public class MultipartFileSerializer extends JsonSerializer<MultipartFile> {

  @Override
  public void serialize(MultipartFile file, JsonGenerator jgen, SerializerProvider provider)
    throws IOException {

    jgen.writeStartObject();
    jgen.writeStringField("name", file.getOriginalFilename());

    byte[] bytes = file.getBytes();
    jgen.writeStringField("data", Base64.getEncoder().encodeToString(bytes));

    jgen.writeEndObject();
  }
}

