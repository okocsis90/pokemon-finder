package com.alea.pokemonfinder.controller;

import com.alea.pokemonfinder.service.PokemonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = "/pokemon")
@RestController
@RequiredArgsConstructor
public class PokemonController {

    private final PokemonService pokemonService;

}
