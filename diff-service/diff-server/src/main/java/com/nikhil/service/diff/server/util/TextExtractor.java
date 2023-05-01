package com.nikhil.service.diff.server.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

public class TextExtractor extends PDFTextStripper {

  private StringBuilder text = new StringBuilder();

  public TextExtractor(MultipartFile file) throws IOException {

    try {
      this.document = PDDocument.load(file.getInputStream());
      this.setSortByPosition(true);
      this.setStartPage(1);
      this.setEndPage(this.document.getNumberOfPages());
      Writer dummy = new OutputStreamWriter(new ByteArrayOutputStream());
      this.writeText(document, dummy);
    } finally {
      if (this.document != null) {
        this.document.close();
      }
    }

  }

  public String getText() {
    return text.toString();
  }

  @Override
  protected void writeString(String line, List<TextPosition> textPositions) throws IOException {

    for (TextPosition textPosition : textPositions) {
      text.append(textPosition);
    }
  }
}
