package com.alea.pokemonfinder.service;

import com.alea.pokemonfinder.model.dto.PokemonDto;
import com.alea.pokemonfinder.model.entity.Pokemon;
import com.alea.pokemonfinder.model.http.PokemonResponse;
import org.springframework.stereotype.Service;

@Service
public class PokemonTransformer {

    public Pokemon transform(final PokemonResponse response) {
        return Pokemon.builder()
                .name(response.getName())
                .baseExperience(response.getBaseExperience())
                .height(response.getHeight())
                .weight(response.getWeight())
                .build();
    }

    public PokemonDto transform(final Pokemon pokemon) {
        return PokemonDto.builder()
                .name(pokemon.getName())
                .baseExperience(pokemon.getBaseExperience())
                .height(pokemon.getHeight())
                .weight(pokemon.getWeight())
                .build();
    }
}
