package com.nikhil.shared.util;

import com.nikhil.shared.CustomMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class MultipartUtils {
  public static MultipartFile reconstructMultipartFile(String fileName, String contentType,
                                                       byte[] bytes) {
    return new CustomMultipartFile(fileName, contentType, bytes);
  }
}
