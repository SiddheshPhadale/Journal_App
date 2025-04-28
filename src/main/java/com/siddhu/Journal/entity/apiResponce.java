package com.siddhu.Journal.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class apiResponce {
    public Current current;

    @Getter
    @Setter
    public class Current{
        @JsonProperty("observation_time")
        public String observationTime;
        public int temperature;
        public int feelslike;
    }
}
