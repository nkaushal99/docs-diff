package com.nikhil.service.highlight.client;

import com.nikhil.service.diff.client.DiffList;
import com.nikhil.service.document.client.OldNewFiles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HighlightRequest {

  private OldNewFiles oldNewFiles;

  private DiffList diffList;

}
