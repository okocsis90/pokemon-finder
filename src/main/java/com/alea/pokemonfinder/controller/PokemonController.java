package com.alea.pokemonfinder.controller;

import com.alea.pokemonfinder.model.dto.PokemonDto;
import com.alea.pokemonfinder.service.PokemonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(path = "/pokemon")
@RestController
@RequiredArgsConstructor
public class PokemonController {

    private final PokemonService pokemonService;

    @GetMapping()
    public ResponseEntity<List<PokemonDto>> getPokemons(@RequestParam final int limit,
                                                        @RequestParam final String sortBy) {
        return ResponseEntity.ok(pokemonService.findAll(limit, sortBy));
    }
}
