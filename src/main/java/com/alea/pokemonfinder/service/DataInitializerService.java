package com.alea.pokemonfinder.service;

import com.alea.pokemonfinder.model.http.PokemonResponse;
import com.alea.pokemonfinder.model.http.SimplePokemonResponseContainer;
import com.alea.pokemonfinder.repository.PokemonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializerService {

    private final PokemonRepository pokemonRepository;
    private final ExternalPokemonService externalPokemonService;
    private final PokemonTransformer pokemonTransformer;

    /**
     * Loads all pokemons into the application database. This is necessary because querying all pokemons creates a
     * heavy load on the external system and latter sorting of the data is also more effective from a local database.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void loadAllPokemonsFromExternalSystem() {
        if (pokemonRepository.findAll().isEmpty()) {
            log.info("No pokemon found in pokemon cache. Connecting external system.");
            externalPokemonService.getAllSimplePokemon().getResults().stream()
                    .map(SimplePokemonResponseContainer.SimplePokemonResponse::getUrl)
                    .forEach(this::savePokemon);
            log.info("Finished caching pokemons.");
        } else {
            log.info("Pokemon cache found, skipping querying from external system. If this is not the desired " +
                    "behaviour, please clear H2 db.");
        }
    }

    /**
     * Saves a pokemon queried from an external url if that pokemon has version 'red'.
     *
     * @param url external url of a given pokemon
     */
    private void savePokemon(final String url) {
        if (isBlank(url)) {
            return;
        }

        final PokemonResponse response = externalPokemonService.getPokemon(url);
        if (externalPokemonService.isRed(response)) {
            pokemonRepository.save(pokemonTransformer.transform(response));
            log.info("Pokemon with name: {} saved to db.", response.getName());
        }
    }
}
