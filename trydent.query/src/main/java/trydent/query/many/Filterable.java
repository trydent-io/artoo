package trydent.query.many;

import trydent.func.$2.FuncInt;
import trydent.func.$2.PredInt;
import trydent.func.Pred;
import trydent.query.Many;
import trydent.query.Queryable;
import trydent.query.internal.Where;

import static trydent.func.$2.FuncInt.identity;
import static trydent.type.Nullability.nonNullable;

public interface Filterable<T> extends Queryable<T> {
  default Many<T> where(final Pred<? super T> where) {
    nonNullable(where, "where");
    return where((index, param) -> where.apply(param));
  }

  default Many<T> where(final PredInt<? super T> where) {
    return where(where, identity());
  }

  default <C> Many<C> ofType(final Class<? extends C> type) {
    nonNullable(type, "type");
    return where((index, it) -> type.isInstance(it), (index, it) -> type.cast(it));
  }

  default <R> Many<R> where(final PredInt<? super T> where, final FuncInt<? super T, ? extends R> select) {
    nonNullable(where, "where");
    nonNullable(select, "select");
    return new Where<T, R>(this, where, select)::iterator;
  }
}
