package com.alea.pokemonfinder.service;

import com.alea.pokemonfinder.model.entity.Pokemon;
import com.alea.pokemonfinder.model.http.PokemonResponse;
import com.alea.pokemonfinder.model.http.SimplePokemonResponseContainer;
import com.alea.pokemonfinder.repository.PokemonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DataInitializerServiceTest {

    private static final String URL_1 = "url1";
    private static final String URL_2 = "url2";
    private static final String RED_VERSION = "red";
    private static final String NON_RED_VERSION = "nonRed";
    private static final String NAME_1 = "name1";
    private static final String NAME_2 = "name2";

    @Mock
    private PokemonRepository pokemonRepository;
    @Mock
    private ExternalPokemonService externalPokemonService;
    @Mock
    private PokemonTransformer pokemonTransformer;

    @InjectMocks
    private DataInitializerService underTest;

    @Test
    public void testLoadAllPokemonsFromExternalSystemWhenCacheFoundThenDoNothing() {
        final Pokemon pokemon = Pokemon.builder().build();

        when(pokemonRepository.findAll()).thenReturn(Collections.singletonList(pokemon));

        underTest.loadAllPokemonsFromExternalSystem();

        verifyZeroInteractions(externalPokemonService);
    }

    @Test
    public void testLoadAllPokemonsFromExternalSystemWhenNoPokemonSavedThenSaveAllRed() {
        final SimplePokemonResponseContainer container = new SimplePokemonResponseContainer();
        final SimplePokemonResponseContainer.SimplePokemonResponse pokemonResponse1 = buildSimpleResponse(URL_1);
        final SimplePokemonResponseContainer.SimplePokemonResponse pokemonResponse2 = buildSimpleResponse(URL_2);
        container.setResults(Arrays.asList(pokemonResponse1, pokemonResponse2));

        final PokemonResponse redPokemon = buildPokemonResponse(buildGameIndexWithVersion(RED_VERSION), NAME_1);
        final PokemonResponse nonRedPokemon = buildPokemonResponse(buildGameIndexWithVersion(NON_RED_VERSION), NAME_2);

        final Pokemon pokemonToSave = Pokemon.builder().build();

        when(pokemonRepository.findAll()).thenReturn(Collections.emptyList());
        when(externalPokemonService.getAllSimplePokemon()).thenReturn(container);
        when(externalPokemonService.getPokemon(URL_1)).thenReturn(redPokemon);
        when(externalPokemonService.getPokemon(URL_2)).thenReturn(nonRedPokemon);
        when(externalPokemonService.isRed(redPokemon)).thenReturn(true);
        when(externalPokemonService.isRed(nonRedPokemon)).thenReturn(false);
        when(pokemonTransformer.transform(redPokemon)).thenReturn(pokemonToSave);

        underTest.loadAllPokemonsFromExternalSystem();

        verify(pokemonRepository).findAll();
        verify(externalPokemonService).getAllSimplePokemon();
        verify(externalPokemonService).getPokemon(URL_1);
        verify(externalPokemonService).getPokemon(URL_2);
        verify(externalPokemonService).isRed(redPokemon);
        verify(externalPokemonService).isRed(nonRedPokemon);
        verify(pokemonTransformer).transform(redPokemon);
        verify(pokemonRepository).save(pokemonToSave);

        verifyNoMoreInteractions(pokemonRepository, externalPokemonService, pokemonTransformer);
    }

    @Test
    public void testLoadAllPokemonsFromExternalSystemWhenResponseHasNullUrlThenDoNothing() {
        final SimplePokemonResponseContainer container = new SimplePokemonResponseContainer();
        final SimplePokemonResponseContainer.SimplePokemonResponse pokemonResponse1 = buildSimpleResponse(URL_1);
        final SimplePokemonResponseContainer.SimplePokemonResponse pokemonResponse2 = buildSimpleResponse(null);

        container.setResults(Arrays.asList(pokemonResponse1, pokemonResponse2));

        final PokemonResponse redPokemon = buildPokemonResponse(buildGameIndexWithVersion(RED_VERSION), NAME_1);

        final Pokemon pokemonToSave = Pokemon.builder().build();

        when(pokemonRepository.findAll()).thenReturn(Collections.emptyList());
        when(externalPokemonService.getAllSimplePokemon()).thenReturn(container);
        when(externalPokemonService.getPokemon(URL_1)).thenReturn(redPokemon);
        when(externalPokemonService.isRed(redPokemon)).thenReturn(true);
        when(pokemonTransformer.transform(redPokemon)).thenReturn(pokemonToSave);

        underTest.loadAllPokemonsFromExternalSystem();

        verify(pokemonRepository).findAll();
        verify(externalPokemonService).getAllSimplePokemon();
        verify(externalPokemonService).getPokemon(URL_1);
        verify(externalPokemonService).isRed(redPokemon);
        verify(pokemonTransformer).transform(redPokemon);
        verify(pokemonRepository).save(pokemonToSave);

        verifyNoMoreInteractions(pokemonRepository, externalPokemonService, pokemonTransformer);
    }

    private PokemonResponse buildPokemonResponse(final PokemonResponse.GameIndex gameIndex, final String name) {
        final PokemonResponse pokemonResponse = new PokemonResponse();
        pokemonResponse.setGameIndices(Collections.singletonList(gameIndex));
        pokemonResponse.setName(name);

        return pokemonResponse;
    }

    private PokemonResponse.GameIndex buildGameIndexWithVersion(final String versionName) {
        final PokemonResponse.Version version = new PokemonResponse.Version();
        version.setName(versionName);
        final PokemonResponse.GameIndex gameIndex = new PokemonResponse.GameIndex();
        gameIndex.setVersion(version);
        return gameIndex;
    }

    private SimplePokemonResponseContainer.SimplePokemonResponse buildSimpleResponse(final String url) {
        final SimplePokemonResponseContainer.SimplePokemonResponse result  =
                new SimplePokemonResponseContainer.SimplePokemonResponse();
        result.setUrl(url);

        return result;
    }
}