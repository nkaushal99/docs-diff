package com.nikhil.service.compare.model;

import java.util.List;

public class DiffList {

  private List<Diff> diffs;

  public DiffList() {

  }

  public DiffList(List<Diff> diffs) {
    this.diffs = diffs;
  }

  public List<Diff> getDiffs() {
    return diffs;
  }

  public void setDiffs(List<Diff> diffs) {
    this.diffs = diffs;
  }

}
