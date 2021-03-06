package io.artoo.lance.literator.cursor.routine.concat;

import io.artoo.lance.literator.Cursor;
import io.artoo.lance.literator.Literator;
import io.artoo.lance.literator.cursor.routine.Routine;

public sealed interface Concat<T> extends Routine<T, Cursor<T>> permits Array, Liter {
  @SafeVarargs
  static <T> Concat<T> array(T... elements) {
    return new Array<>(elements);
  }

  static <T> Concat<T> liter(Literator<T> literator) {
    return new Liter<>(literator);
  }

}
