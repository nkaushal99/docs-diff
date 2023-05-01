package com.nikhil.service.document.client;


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
public class OldNewFiles implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private MultipartFileImpl oldFile;

  private MultipartFileImpl newFile;

}
