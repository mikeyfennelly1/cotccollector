package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.nio.charset.StandardCharsets;
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

    @JsonIgnore
    public byte[] getBytes() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this).getBytes(StandardCharsets.UTF_8);
    }
}
