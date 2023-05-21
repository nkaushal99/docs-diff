package com.nikhil.shared;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class Event {
  private String requestId;
}
