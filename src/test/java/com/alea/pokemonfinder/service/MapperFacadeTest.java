package com.alea.pokemonfinder.service;

import com.alea.pokemonfinder.exception.PokemonFinderException;
import com.alea.pokemonfinder.model.http.PokemonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MapperFacadeTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private MapperFacade underTest;

    @Test
    public void testReadValueWhenThrowsExceptionThenThrowCustomException() throws IOException {
        expectedException.expect(PokemonFinderException.class);
        expectedException.expectMessage("Error parsing data from inputStream.");

        final InputStream inputStream = mock(InputStream.class);

        when(objectMapper.readValue(inputStream, PokemonResponse.class)).thenThrow(new IOException());

        underTest.readValue(inputStream, PokemonResponse.class);
    }

    @Test
    public void testReadValueWhenHappyPathThenReturnProperly() throws IOException {
        final InputStream inputStream = mock(InputStream.class);
        final PokemonResponse expected = new PokemonResponse();

        when(objectMapper.readValue(inputStream, PokemonResponse.class)).thenReturn(expected);

        assertThat(underTest.readValue(inputStream, PokemonResponse.class)).isEqualTo(expected);
    }

}
