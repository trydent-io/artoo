package io.artoo.value;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record Int16(short eval) implements Numeral<Short, Int16> {
  public static final Int16 ZERO = new Int16((short) 0);
  public static final Int16 ONE = new Int16((short) 1);

  @Override
  public final @NotNull Short box() {
    return eval;
  }

  @Contract("_ -> new")
  @Override
  public <L extends Number, V extends Record & Numeral<L, V>> @NotNull Int16 add(@NotNull V value) {
    return new Int16((short) (eval + value.box().shortValue()));
  }

  @Contract("_ -> new")
  @Override
  public <L extends Number, V extends Record & Numeral<L, V>> @NotNull Int16 div(@NotNull V value) {
    return new Int16((short) (eval / value.box().shortValue()));
  }

  @Override
  @Contract(" -> new")
  public @NotNull Int16 inc() {
    return new Int16((short) (eval + 1));
  }
}


