package oak.collect.cursor;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.Objects.nonNull;
import static oak.collect.cursor.LocalIndex.zero;

final class Forward<E> implements Cursor<E> {
  private final E[] es;
  private final AtomicInteger index;

  Forward(E[] es) {
    this(es, new AtomicInteger(0));
  }
  private Forward(E[] es, AtomicInteger index) {
    this.es = es;
    this.index = index;
  }

  @Override
  public final boolean hasNext() {
    return nonNull(es) && es.length > 0 && index.get() < es.length;
  }

  @Override
  public final E next() {
    return es.length > 0 ? es[index.getAndIncrement()] : null;
  }
}
