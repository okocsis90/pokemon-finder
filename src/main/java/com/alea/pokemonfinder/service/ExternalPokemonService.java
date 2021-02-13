package com.alea.pokemonfinder.service;

import com.alea.pokemonfinder.model.http.PokemonResponse;
import com.alea.pokemonfinder.model.http.SimplePokemonResponseContainer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpUriRequest;
import org.springframework.stereotype.Service;

/**
 * Responsible for setting up the communication with the external pokemon API.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ExternalPokemonService {

    private static final String POKEMON_API_URL = "https://pokeapi.co/api/v2/pokemon";
    private static final String LIMIT = "?limit=2000";
    private static final String RED_VERSION = "red";

    private final HttpFacade httpFacade;
    private final MapperFacade mapperFacade;

    /**
     * Fetches all pokemon in a simple format which will contain the name and the url of that pokemon. It can be used
     * later to fetch all information about that given pokemon. As it is unlikely that new pokemons will be added to the
     * external system, the limit is set as a constant. This way no pagination logic is needed and all the pokemons can
     * be queried in one single call.
     *
     * @return {@link SimplePokemonResponseContainer}
     */
    public SimplePokemonResponseContainer getAllSimplePokemon() {
        final HttpUriRequest request = httpFacade.buildGetRequest(POKEMON_API_URL + LIMIT);

        return mapperFacade.readValue(httpFacade.sendRequest(request), SimplePokemonResponseContainer.class);
    }

    /**
     * Fetches all information about a given pokemon. This is done using the url pointing to the external api.
     *
     * @param url to use for fetching the given pokemon.
     * @return {@link PokemonResponse} which contains all necessary information our system needs.
     */
    public PokemonResponse getPokemon(final String url) {
        final HttpUriRequest request = httpFacade.buildGetRequest(url);

        return mapperFacade.readValue(httpFacade.sendRequest(request), PokemonResponse.class);
    }

    /**
     * Responsible for checking if the given pokemon has version 'red'.
     *
     * @param pokemon as coming from the external system.
     * @return whether the pokemon has version red.
     */
    public boolean isRed(final PokemonResponse pokemon) {
        if (pokemon == null) {
            return false;
        }
        return pokemon.getGameIndices().stream()
                .anyMatch(index -> index.getVersion() != null && RED_VERSION.equals(index.getVersion().getName()));
    }
}
