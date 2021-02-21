package io.artoo.lance.literator.cursor.impl;

import io.artoo.lance.literator.cursor.Cursor;
import io.artoo.lance.literator.Literator;
import io.artoo.lance.literator.cursor.routine.Routine;

public interface Transformer<T> extends Literator<T> {
  default <R, C extends Cursor<R>> C to(Routine<T, C> routine) {
    return this.as(routine);
  }
  <R> R as(Routine<T, R> routine);
}

