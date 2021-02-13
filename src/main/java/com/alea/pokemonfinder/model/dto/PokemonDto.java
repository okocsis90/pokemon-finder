package com.alea.pokemonfinder.model.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PokemonDto {

    final String name;
    final Long weight;
    final Long height;
    final Long baseExperience;
}
