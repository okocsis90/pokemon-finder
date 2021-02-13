package com.alea.pokemonfinder.service;

import com.alea.pokemonfinder.model.http.PokemonResponse;
import com.alea.pokemonfinder.model.http.SimplePokemonResponseContainer;
import org.apache.http.client.methods.HttpUriRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.InputStream;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ExternalPokemonServiceTest {

    private static final String POKEMON_API_URL = "https://pokeapi.co/api/v2/pokemon";
    private static final String LIMIT = "?limit=2000";
    private static final String RED_VERSION = "red";
    private static final String NON_RED_VERSION = "nonRed";
    private static final String POKEMON_URL = "pokemonUrl";

    @Mock
    private HttpFacade httpFacade;
    @Mock
    private MapperFacade mapperFacade;

    @InjectMocks
    private ExternalPokemonService underTest;

    @Test
    public void testGetAllSimplePokemonWhenCalledThenCallNecessaryMethodsProperly() {
        final HttpUriRequest request = mock(HttpUriRequest.class);
        final InputStream resultInputStream = mock(InputStream.class);
        final SimplePokemonResponseContainer expected = new SimplePokemonResponseContainer();

        when(httpFacade.buildGetRequest(POKEMON_API_URL + LIMIT)).thenReturn(request);
        when(httpFacade.sendRequest(request)).thenReturn(resultInputStream);
        when(mapperFacade.readValue(resultInputStream, SimplePokemonResponseContainer.class)).thenReturn(expected);

        final SimplePokemonResponseContainer actual = underTest.getAllSimplePokemon();

        assertThat(actual).isEqualTo(expected);

        verify(httpFacade).buildGetRequest(POKEMON_API_URL + LIMIT);
        verify(httpFacade).sendRequest(request);
        verify(mapperFacade).readValue(resultInputStream, SimplePokemonResponseContainer.class);

        verifyNoMoreInteractions(httpFacade, mapperFacade);
    }

    @Test
    public void testGetPokemonWhenCalledThenCallNecessaryMethodsProperly() {
        final HttpUriRequest request = mock(HttpUriRequest.class);
        final InputStream resultInputStream = mock(InputStream.class);
        final PokemonResponse expected = new PokemonResponse();

        when(httpFacade.buildGetRequest(POKEMON_URL)).thenReturn(request);
        when(httpFacade.sendRequest(request)).thenReturn(resultInputStream);
        when(mapperFacade.readValue(resultInputStream, PokemonResponse.class)).thenReturn(expected);

        final PokemonResponse actual = underTest.getPokemon(POKEMON_URL);

        assertThat(actual).isEqualTo(expected);

        verify(httpFacade).buildGetRequest(POKEMON_URL);
        verify(httpFacade).sendRequest(request);
        verify(mapperFacade).readValue(resultInputStream, PokemonResponse.class);

        verifyNoMoreInteractions(httpFacade, mapperFacade);
    }

    @Test
    public void testIsRedWhenPokemonIsNullThenReturnFalse() {
        assertThat(underTest.isRed(null)).isFalse();
    }

    @Test
    public void testIsRedWhenIndexVersionIsNullThenReturnFalse() {
        final PokemonResponse pokemonResponse = new PokemonResponse();
        final PokemonResponse.GameIndex gameIndex = new PokemonResponse.GameIndex();
        gameIndex.setVersion(null);
        pokemonResponse.setGameIndices(Collections.singletonList(gameIndex));

        assertThat(underTest.isRed(pokemonResponse)).isFalse();
    }

    @Test
    public void testIsRedWhenNotRedThenReturnFalse() {
        final PokemonResponse pokemonResponse = new PokemonResponse();
        final PokemonResponse.GameIndex gameIndex = new PokemonResponse.GameIndex();
        final PokemonResponse.Version version = new PokemonResponse.Version();
        version.setName(NON_RED_VERSION);
        gameIndex.setVersion(version);
        pokemonResponse.setGameIndices(Collections.singletonList(gameIndex));

        assertThat(underTest.isRed(pokemonResponse)).isFalse();
    }

    @Test
    public void testIsRedWhenRedThenReturnFalse() {
        final PokemonResponse pokemonResponse = new PokemonResponse();
        final PokemonResponse.GameIndex gameIndex = new PokemonResponse.GameIndex();
        final PokemonResponse.Version version = new PokemonResponse.Version();
        version.setName(RED_VERSION);
        gameIndex.setVersion(version);
        pokemonResponse.setGameIndices(Collections.singletonList(gameIndex));

        assertThat(underTest.isRed(pokemonResponse)).isTrue();
    }
}
