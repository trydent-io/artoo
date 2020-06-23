package io.artoo.lance.cursor;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public enum Default implements Cursor<Object> {
  None;

  @Override
  public final boolean hasNext() {
    return false;
  }

  @Nullable
  @Contract(pure = true)
  @Override
  public final Object next() {
    return null;
  }
}