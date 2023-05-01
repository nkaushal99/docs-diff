package com.nikhil.service.highlight.client;

import com.nikhil.service.document.client.MultipartFileImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HighlightResponse implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private MultipartFileImpl oldFileHighlighted;

  private MultipartFileImpl newFileHighlighted;

}
