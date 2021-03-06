package io.artoo.parry.eventbus.impl.codecs;

import io.artoo.parry.buffer.Buffer;
import io.artoo.parry.eventbus.MessageCodec;

public class BufferMessageCodec implements MessageCodec<Buffer, Buffer> {

  @Override
  public void encodeToWire(Buffer buffer, Buffer b) {
    buffer.appendInt(b.length());
    buffer.appendBuffer(b);
  }

  @Override
  public Buffer decodeFromWire(int pos, Buffer buffer) {
    int length = buffer.getInt(pos);
    pos += 4;
    return buffer.getBuffer(pos, pos + length);
  }

  @Override
  public Buffer transform(Buffer b) {
    return b.copy();
  }

  @Override
  public String name() {
    return "buffer";
  }

  @Override
  public byte systemCodecID() {
    return 11;
  }
}
