/*
 * Copyright (c) 2011-2019 Contributors to the Eclipse Foundation
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the Apache License, Version 2.0
 * which is available at https://www.apache.org/licenses/LICENSE-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0 OR Apache-2.0
 */

package io.artoo.parry.eventbus;

import io.artoo.parry.async.AsyncResult;
import io.artoo.parry.async.Future;
import io.artoo.parry.async.Handler;

/**
 * Represents a stream of message that can be written to.
 * <p>
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */

public interface MessageProducer<T> {

  /**
   * Update the delivery options of this producer.
   *
   * @param options the new options
   * @return this producer object
   */
  MessageProducer<T> deliveryOptions(DeliveryOptions options);

  /**
   * @return The address to which the producer produces messages.
   */
  String address();

  /**
   * Write a message to the event-bus, either sending or publishing.
   *
   * @param body the message body
   * @param handler the handler called when the message has been successfully or failed to be written, this is not a delivery
   *                guarantee
   */
  void write(T body, Handler<AsyncResult<Void>> handler);

  /**
   * Like {@link #write(Object, Handler)} but returns a {@code Future} of the asynchronous result
   */
  Future<Void> write(T body);

  /**
   * Closes the producer, this method should be called when the message producer is not used anymore.
   *
   * @return a future completed with the result
   */
  Future<Void> close();

  /**
   * Same as {@link #close()} but with an {@code handler} called when the operation completes
   */
  void close(Handler<AsyncResult<Void>> handler);
}