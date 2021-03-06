package io.artoo.lance.literator.cursor.routine.sort;

import io.artoo.lance.func.Func;
import io.artoo.lance.literator.Cursor;
import io.artoo.lance.literator.cursor.routine.Routine;
import io.artoo.lance.query.many.Ordering;

import static io.artoo.lance.query.many.Ordering.Arrange.asc;

public sealed interface Sort<T> extends Routine<T, Cursor<T>> permits Default, Arranged {
  record By<T, R>(Func.Uni<? super T, ? extends R> field, Ordering.Arrange arrange) {
    public By(Func.Uni<? super T, ? extends R> field) {
      this(field, asc);
    }

    public static <T, R> By<T, R> with(Func.Uni<? super T, ? extends R> field, Ordering.Arrange arrange) {
      return new By<>(field, arrange);
    }

    public int arranged(int compared) { return arrange.equals(asc) ? compared : ~compared; }
    public Object fieldOf(T entry) { return field.apply(entry); }
  }

  @SafeVarargs
  static <T> Sort<T> arranged(By<T, Object>... bys) {
    return new Arranged<>(bys);
  }

  static <T> Sort<T> byDefault() {
    return new Default<>();
  }
}
