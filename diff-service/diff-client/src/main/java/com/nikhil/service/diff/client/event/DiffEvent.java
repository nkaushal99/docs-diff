package com.nikhil.service.diff.client.event;

import com.nikhil.service.diff.client.DiffList;
import com.nikhil.shared.Event;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DiffEvent extends Event {
  private DiffList diffList;
}
