package com.example.simulationmanager.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParameterValues {
    @JsonProperty("is_bpx")
    private boolean isBpx;

    @JsonProperty("parameter_value")
    private String parameterValue; // "ECM_Example"

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("updated_parameters")
    private Map<String, Object> updatedParameters;
}
