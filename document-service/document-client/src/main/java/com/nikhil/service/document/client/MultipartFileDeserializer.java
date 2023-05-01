package com.nikhil.service.document.client;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

public class MultipartFileDeserializer extends JsonDeserializer<MultipartFile> {

  @Override
  public MultipartFile deserialize(JsonParser jp, DeserializationContext ctxt)
    throws IOException {

    ObjectCodec codec = jp.getCodec();
    JsonNode node = codec.readTree(jp);

    String name = node.get("name").asText();
    byte[] bytes = Base64.getDecoder().decode(node.get("data").asText());

    // Create a new instance of MultipartFile
    MultipartFileImpl file = new MultipartFileImpl(name, bytes);
    file.setName(name);
    file.setBytes(bytes);
    return file;
  }
}

