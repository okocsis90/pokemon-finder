package com.alea.pokemonfinder.model.http;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimplePokemonResponseContainer {

    private List<SimplePokemonResponse> results;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SimplePokemonResponse {

        private String name;
        private String url;
    }
}
