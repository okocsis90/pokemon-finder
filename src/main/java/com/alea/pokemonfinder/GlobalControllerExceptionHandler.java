package com.alea.pokemonfinder;

import com.alea.pokemonfinder.exception.PokemonFinderException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {
	
    @ExceptionHandler(PokemonFinderException.class)
    public ResponseEntity<Map<String, Object>> handleExceptions(PokemonFinderException e) {
        log.error("Pokemon API exception: ", e);
        return buildResponse(e);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleAllExceptions(Exception e) {
        log.error("Unexpected exception: ", e);
        return buildResponse(e);
    }

	private ResponseEntity<Map<String, Object>> buildResponse(Exception e){
		Map<String, Object> body = new HashMap<>();
		body.put("message", e.getMessage());
		body.put("exception", e.getClass().getName());
		body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		body.put("status_code", HttpStatus.INTERNAL_SERVER_ERROR.value());
		return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}