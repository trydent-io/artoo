package io.artoo.lance.literator.cursor.routine.join;

import io.artoo.lance.func.Func;
import io.artoo.lance.func.Pred;
import io.artoo.lance.literator.Cursor;
import io.artoo.lance.literator.Literator;
import io.artoo.lance.query.many.Joinable.Joined;

import java.util.Iterator;
import java.util.Objects;

final class Inner<T, R> implements Join<T, Cursor<Joined<T, R>>> {
  private final Cursor<R> cursor;
  private final Pred.Bi<? super T, ? super R> pred;

  Inner(final Cursor<R> cursor) {
    this(
      cursor,
      Objects::equals
    );
  }
  Inner(final Cursor<R> cursor, final Pred.Bi<? super T, ? super R> pred) {this.cursor = cursor;
    this.pred = pred;
  }

  @Override
  public Func.Uni<T[], Cursor<Joined<T, R>>> onArray() {
    return ts -> cursor.to(new Nested<>(ts, pred));
  }

  @Override
  public Func.Uni<Literator<T>, Cursor<Joined<T, R>>> onLiterator() {
    return null;
  }

  @Override
  public Func.Uni<Iterator<T>, Cursor<Joined<T, R>>> onIterator() {
    return null;
  }
}