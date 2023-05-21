package com.nikhil.service.highlight.client.event;

import com.nikhil.service.highlight.client.HighlightResponse;
import com.nikhil.shared.Event;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HighlightEvent extends Event {
  private HighlightResponse highlightResponse;
}
