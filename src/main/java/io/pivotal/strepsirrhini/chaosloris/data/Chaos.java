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

package io.pivotal.strepsirrhini.chaosloris.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import static lombok.AccessLevel.PACKAGE;

@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor(access = PACKAGE)
public class Chaos {

    @JsonIgnore
    @ManyToOne
    private Application application;

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    private Double probability;

    @JsonIgnore
    @ManyToOne
    private Schedule schedule;

    /**
     * Create a new instance
     *
     * @param application the application to apply chaos to
     * @param probability the probability of an instance being destroyed
     * @param schedule    the schedule to apply chaos on
     */
    public Chaos(Application application, Double probability, Schedule schedule) {
        this.application = application;
        this.probability = probability;
        this.schedule = schedule;
    }

}
