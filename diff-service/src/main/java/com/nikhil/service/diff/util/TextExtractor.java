package com.nikhil.service.diff.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

public class TextExtractor extends PDFTextStripper {

  private StringBuilder text = new StringBuilder();

  public TextExtractor(String inputFilename) throws IOException {

    try {
      this.document = PDDocument.load(new File(inputFilename));
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
