package com.alea.pokemonfinder.model.http;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PokemonResponse {

    private String name;
    @JsonProperty("game_indices")
    private List<GameIndex> gameIndices;
    private Long height;
    private Long weight;
    @JsonProperty("base_experience")
    private Long baseExperience;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GameIndex {

        @JsonProperty("game_index")
        private String gameIndex;
        private Version version;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Version {

        private String name;
    }
}
