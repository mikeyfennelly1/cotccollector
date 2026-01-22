package com.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysinfoMessage {
    @JsonProperty("mac_address")
    private String macAddress;
    @JsonProperty("read_time")
    private long readTime;
    @JsonProperty("metrics")
    private Map<String, Double> metrics;
}
