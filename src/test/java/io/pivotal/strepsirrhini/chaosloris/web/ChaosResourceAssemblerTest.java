/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.pivotal.strepsirrhini.chaosloris.web;

import io.pivotal.strepsirrhini.chaosloris.AbstractIntegrationTest;
import io.pivotal.strepsirrhini.chaosloris.data.Application;
import io.pivotal.strepsirrhini.chaosloris.data.ApplicationRepository;
import io.pivotal.strepsirrhini.chaosloris.data.Chaos;
import io.pivotal.strepsirrhini.chaosloris.data.ChaosRepository;
import io.pivotal.strepsirrhini.chaosloris.data.Event;
import io.pivotal.strepsirrhini.chaosloris.data.EventRepository;
import io.pivotal.strepsirrhini.chaosloris.data.Schedule;
import io.pivotal.strepsirrhini.chaosloris.data.ScheduleRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ChaosResourceAssemblerTest extends AbstractIntegrationTest {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ChaosRepository chaosRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ChaosResourceAssembler resourceAssembler;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Test
    public void toResource() {
        Application application = new Application(UUID.randomUUID());
        this.applicationRepository.saveAndFlush(application);

        Schedule schedule = new Schedule("test-expression", "test-name");
        this.scheduleRepository.saveAndFlush(schedule);

        Chaos chaos = new Chaos(application, 0.1, schedule);
        this.chaosRepository.saveAndFlush(chaos);

        Event event = new Event(chaos, Instant.now(), Collections.emptyList(), Integer.MIN_VALUE);
        this.eventRepository.saveAndFlush(event);

        ChaosResourceAssembler.ChaosResource resource = this.resourceAssembler.toResource(chaos);

        assertThat(resource.getContent()).isSameAs(chaos);
        assertThat(resource.getLinks()).hasSize(4);
        assertThat(resource.getLink("application")).isNotNull();
        assertThat(resource.getLink("event")).isNotNull();
        assertThat(resource.getLink("schedule")).isNotNull();
    }
}
