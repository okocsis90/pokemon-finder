package com.alea.pokemonfinder.service;

import com.alea.pokemonfinder.exception.PokemonFinderException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

/**
 * Facade for using the {@link ObjectMapper} class.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MapperFacade {

    private final ObjectMapper objectMapper;

    public <T> T readValue(final InputStream inputStream, final Class<T> clazz) {
        try {
            return objectMapper.readValue(inputStream, clazz);
        } catch (IOException e) {
            log.error("Error parsing data: ", e);
            throw new PokemonFinderException("Error parsing data from inputStream.");
        }
    }
}
