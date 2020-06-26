package io.artoo.lance.query.cursor;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PipeTest {
  @Test
  void shouldIterateAllElements() {
    final Iterable<Integer> integers = () -> Cursor.pipe(1, 2, 3, 4);

    assertThat(integers).containsExactly(1, 2, 3, 4);
  }

  @Test
  void shouldNotIterateOnEmpty() {
    final Iterable<Integer> integers = Cursor::pipe;

    assertThat(integers).isEmpty();
  }

  @Test
  @Disabled
  void shouldIterateOnBigList() {
    List<Integer> list = new ArrayList<>();
    var bound = Short.MAX_VALUE * Byte.MAX_VALUE * Byte.MAX_VALUE;
    for (var it = 0; it < bound; it++) {
      var i = it * 2;
      Integer integer = i;
      list.add(integer);
    }

    final Iterable<Integer> integers = () -> Cursor.pipe(list.toArray(new Integer[]{}));

    assertThat(integers).isNotEmpty();
  }
}
