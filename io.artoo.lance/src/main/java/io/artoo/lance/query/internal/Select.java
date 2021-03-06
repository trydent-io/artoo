package io.artoo.lance.query.internal;

import io.artoo.lance.func.Func;
import io.artoo.lance.query.Tail;
import io.artoo.lance.task.Atomic;

public interface Select<T, R> extends Func.Uni<T, Tail<T, R, Select<T, R>>> {
  static <T, R> Atomic<Select<T, R>> indexed(final Func.Uni<? super T, ? extends R> select) {
    return indexed((i, it) -> select.tryApply(it));
  }

  static <T, R> Atomic<Select<T, R>> indexed(final Func.Bi<? super Integer, ? super T, ? extends R> select) {
    return Atomic.reference(new Indexed<>(select));
  }

  final class Indexed<T, R> implements Select<T, R> {
    private final Func.Bi<? super Integer, ? super T, ? extends R> select;

    private final int index;

    Indexed(final Func.Bi<? super Integer, ? super T, ? extends R> select) {
      this(0, select);
    }
    Indexed(final int index, final Func.Bi<? super Integer, ? super T, ? extends R> select) {
      assert select != null;
      this.index = index;
      this.select = select;
    }

    @Override
    public Tail<T, R, Select<T, R>> tryApply(final T t) throws Throwable {
      return
        new Tail<>(
          new Indexed<>(index + 1, select),
          select.tryApply(index, t)
        );
    }
  }
}
