package com.alea.pokemonfinder.service;

import com.alea.pokemonfinder.model.dto.PokemonDto;
import com.alea.pokemonfinder.model.entity.Pokemon;
import com.alea.pokemonfinder.repository.PokemonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Internal service for fetching pokemons from the database.
 */
@Service
@RequiredArgsConstructor
public class PokemonService {

    private final PokemonRepository repository;
    private final PokemonTransformer transformer;

    /**
     * Responsible for fetching all pokemons based on the parameters. Sorting happens descending based on the given sort
     * field.
     *
     * @param limit maximum number of pokemons to be returned.
     * @param sortBy field to sort by.
     * @return sorted list of given number of {@link PokemonDto}.
     */
    public List<PokemonDto> findAll(final int limit, final String sortBy) {
        final PageRequest pageRequest = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, sortBy));
        final Page<Pokemon> page = repository.findAll(pageRequest);
        return page.get().map(transformer::transform).collect(Collectors.toList());
    }
}
