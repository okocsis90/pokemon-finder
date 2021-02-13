package com.alea.pokemonfinder.exception;

public class PokemonFinderException extends RuntimeException {

    public PokemonFinderException(final String message) {
        super(message);
    }

    public PokemonFinderException(final String message, final Throwable e) {
        super(message, e);
    }
}
