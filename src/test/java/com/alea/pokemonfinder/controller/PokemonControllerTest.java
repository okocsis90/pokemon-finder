package com.alea.pokemonfinder.controller;

import com.alea.pokemonfinder.model.dto.PokemonDto;
import com.alea.pokemonfinder.service.PokemonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PokemonControllerTest {

    private static final int LIMIT = 1;
    private static final String SORT_BY = "height";

    @Mock
    private PokemonService pokemonService;

    @InjectMocks
    private PokemonController underTest;

    @Test
    public void testGetPokemonsWhenCalledThenCallServiceMethod() {
        final List<PokemonDto> expected = Collections.emptyList();

        when(pokemonService.findAll(LIMIT, SORT_BY)).thenReturn(expected);

        final ResponseEntity<List<PokemonDto>> actual = underTest.getPokemons(LIMIT, SORT_BY);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual.getBody()).isEqualTo(expected);

        verify(pokemonService).findAll(LIMIT, SORT_BY);
        verifyNoMoreInteractions(pokemonService);
    }

}