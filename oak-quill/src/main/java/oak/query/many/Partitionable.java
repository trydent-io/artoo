package oak.query.many;

import oak.func.$2.IntPre;
import oak.func.Pre;
import oak.query.Many;
import oak.query.Queryable;

import java.util.ArrayList;

import static oak.type.Nullability.nonNullable;

public interface Partitionable<T> extends Queryable<T> {
  default Many<T> skip(final int until) {
    return () -> {
      var skip = 0;
      var array = new ArrayList<T>();
      for (final var it : this)
        if (skip++ >= until)
          array.add(it);
      return array.iterator();
    };
  }

  default Many<T> skipWhile(final Pre<? super T> where) {
    nonNullable(where, "filter");
    return skipWhile((index, param) -> where.test(param));
  }

  default Many<T> skipWhile(final IntPre<? super T> where) {
    nonNullable(where, "where");
    return () -> {
      final var result = new ArrayList<T>();
      var keepSkipping = true;
      var index = 0;
      for (final var cursor = this.iterator(); cursor.hasNext(); index++) {
        var value = cursor.next();
        if (!where.verify(index, value) || !keepSkipping) {
          result.add(value);
          keepSkipping = false;
        }
      }
      return result.iterator();
    };
  }

  default Many<T> take(final int until) {
    return () -> {
      var take = 0;
      var seq = new ArrayList<T>();
      for (final var it : this)
        if (take++ < until)
          seq.add(it);
      return seq.iterator();
    };
  }

  default Many<T> takeWhile(final Pre<? super T> where) {
    nonNullable(where, "where");
    return takeWhile((index, param) -> where.test(param));
  }

  default Many<T> takeWhile(final IntPre<? super T> where) {
    nonNullable(where, "where");
    return () -> {
      final var result = new ArrayList<T>();
      var keepTaking = true;
      var index = 0;
      for (var cursor = this.iterator(); cursor.hasNext() && keepTaking; index++) {
        final var it = cursor.next();
        if (where.verify(index, it)) {
          result.add(it);
        } else {
          keepTaking = false;
        }
      }
      return result.iterator();
    };
  }
}
