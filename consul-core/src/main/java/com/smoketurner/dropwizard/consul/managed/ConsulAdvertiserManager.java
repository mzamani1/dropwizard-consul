/*
 * Copyright © 2019 Smoke Turner, LLC (github@smoketurner.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.smoketurner.dropwizard.consul.managed;

import com.smoketurner.dropwizard.consul.core.ConsulAdvertiser;
import io.dropwizard.lifecycle.Managed;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ScheduledExecutorService;

public class ConsulAdvertiserManager implements Managed {

  private final ConsulAdvertiser advertiser;
  private final Optional<ScheduledExecutorService> scheduler;

  /**
   * Constructor
   *
   * @param advertiser Consul advertiser
   * @param scheduler Optional retry scheduler
   */
  public ConsulAdvertiserManager(
      final ConsulAdvertiser advertiser, final Optional<ScheduledExecutorService> scheduler) {
    this.advertiser = Objects.requireNonNull(advertiser, "advertiser == null");
    this.scheduler = Objects.requireNonNull(scheduler, "scheduler == null");
  }

  @Override
  public void start() throws Exception {
    // the advertiser is register as a Jetty startup listener
  }

  @Override
  public void stop() throws Exception {
    advertiser.deregister();
    scheduler.ifPresent(ScheduledExecutorService::shutdownNow);
  }
}
