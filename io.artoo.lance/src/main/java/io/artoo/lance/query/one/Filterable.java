package io.artoo.lance.query.one;

import io.artoo.lance.func.Pred;
import io.artoo.lance.query.One;
import io.artoo.lance.query.Queryable;
import io.artoo.lance.query.internal.NotOfType;
import io.artoo.lance.query.internal.OfType;
import io.artoo.lance.query.internal.Where;

public interface Filterable<T> extends Queryable<T> {
  default One<T> where(final Pred.Uni<? super T> where) {
    return () -> cursor().map(new Where<>((i, it) -> where.tryTest(it)));
  }

  default <R> One<R> ofType(final Class<? extends R> type) {
    return () -> cursor().map(new OfType<>(type));
  }

  default <R> One<T> notOfType(final Class<? extends R> type) {
    return () -> cursor().map(new NotOfType<>(type));
  }
}
