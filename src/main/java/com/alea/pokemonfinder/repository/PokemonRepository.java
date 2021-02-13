package com.alea.pokemonfinder.repository;

import com.alea.pokemonfinder.model.entity.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PokemonRepository extends JpaRepository<Pokemon, Long> {
}
