package com.nikhil.service.document.client.event;

import com.nikhil.service.document.client.OldNewFiles;
import com.nikhil.shared.Event;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CompareEvent extends Event {
  private OldNewFiles oldNewFiles;
}
