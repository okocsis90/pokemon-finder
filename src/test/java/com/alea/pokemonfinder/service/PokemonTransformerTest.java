package com.alea.pokemonfinder.service;

import com.alea.pokemonfinder.model.dto.PokemonDto;
import com.alea.pokemonfinder.model.entity.Pokemon;
import com.alea.pokemonfinder.model.http.PokemonResponse;
import org.junit.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class PokemonTransformerTest {

    private static final String NAME = "name";
    private static final Long HEIGHT = 1L;
    private static final Long WEIGHT = 2L;
    private static final Long BASE_EXPERIENCE = 3L;
    private static final String VERSION_NAME = "versionName";

    private final PokemonTransformer underTest = new PokemonTransformer();

    @Test
    public void testTransformWhenCalledWithPokemonResponseThenReturnPokemon() {
        final PokemonResponse response = buildFullPokemonResponse();

        final Pokemon actual = underTest.transform(response);

        assertThat(actual.getName()).isEqualTo(NAME);
        assertThat(actual.getHeight()).isEqualTo(HEIGHT);
        assertThat(actual.getWeight()).isEqualTo(WEIGHT);
        assertThat(actual.getBaseExperience()).isEqualTo(BASE_EXPERIENCE);
    }

    @Test
    public void testTransformWhenCalledWithPokemonThenReturnPokemonDto() {
        final Pokemon pokemon = buildFullPokemon();

        final PokemonDto actual = underTest.transform(pokemon);

        assertThat(actual.getName()).isEqualTo(NAME);
        assertThat(actual.getHeight()).isEqualTo(HEIGHT);
        assertThat(actual.getWeight()).isEqualTo(WEIGHT);
        assertThat(actual.getBaseExperience()).isEqualTo(BASE_EXPERIENCE);
    }

    private PokemonResponse buildFullPokemonResponse() {
        final PokemonResponse response = new PokemonResponse();
        final PokemonResponse.GameIndex gameIndex = new PokemonResponse.GameIndex();
        final PokemonResponse.Version version = new PokemonResponse.Version();
        version.setName(VERSION_NAME);
        gameIndex.setVersion(version);
        response.setGameIndices(Collections.singletonList(gameIndex));
        response.setName(NAME);
        response.setHeight(HEIGHT);
        response.setWeight(WEIGHT);
        response.setBaseExperience(BASE_EXPERIENCE);

        return response;
    }

    private Pokemon buildFullPokemon() {
        return Pokemon.builder()
                .name(NAME)
                .height(HEIGHT)
                .weight(WEIGHT)
                .baseExperience(BASE_EXPERIENCE)
                .build();
    }
}
