package io.artoo.lance.query.internal;

import io.artoo.lance.func.Func;
import io.artoo.lance.query.Tail;
import io.artoo.lance.task.Atomic;

public interface Average<T, N extends Number> extends Func.Uni<T, Tail<T, Double, Average<T, N>>> {
  static <T, N extends Number> Atomic<Average<T, N>> accumulated(Func.Uni<? super T, ? extends N> func) {
    return Atomic.reference(new Accumulated<>(null, 0, func));
  }

  final class Accumulated<T, N extends Number> implements Average<T, N> {
    private final Double accumulated;
    private final int counted;
    private final Func.Uni<? super T, ? extends N> select;

    Accumulated(final Double accumulated, final int counted, final Func.Uni<? super T, ? extends N> select) {
      this.accumulated = accumulated;
      this.counted = counted;
      this.select = select;
    }

    @Override
    public Tail<T, Double, Average<T, N>> tryApply(final T it) throws Throwable {
      final var selected = select.tryApply(it);

      if (selected == null)
        return new Tail<>(this, accumulated);

      final var acc = counted == 0 ? selected.doubleValue() : accumulated + selected.doubleValue();
      final var cc = counted + 1;
      return
        new Tail<>(
          new Accumulated<>(acc, cc, select),
          acc / cc
        );
    }
  }
}
