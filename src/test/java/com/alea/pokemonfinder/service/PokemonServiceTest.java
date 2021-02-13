package com.alea.pokemonfinder.service;

import com.alea.pokemonfinder.model.dto.PokemonDto;
import com.alea.pokemonfinder.model.entity.Pokemon;
import com.alea.pokemonfinder.repository.PokemonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PokemonServiceTest {

    private static final int LIMIT = 1;
    private static final String SORT_BY = "sortBy";

    @Mock
    private PokemonRepository repository;
    @Mock
    private PokemonTransformer transformer;

    @InjectMocks
    private PokemonService underTest;

    @Captor
    private ArgumentCaptor<PageRequest> pageRequestArgumentCaptor;

    @Test
    public void testFindAllWhenCalledThenCallNecessaryMethodsProperly() {
        final Pokemon foundPokemon = Pokemon.builder().build();
        final Page<Pokemon> foundPage = new PageImpl<>(Collections.singletonList(foundPokemon));
        final PokemonDto expected = PokemonDto.builder().build();

        when(repository.findAll(any(PageRequest.class))).thenReturn(foundPage);
        when(transformer.transform(foundPokemon)).thenReturn(expected);

        assertThat(underTest.findAll(LIMIT, SORT_BY).get(0)).isEqualTo(expected);

        verify(repository).findAll(pageRequestArgumentCaptor.capture());

        assertThat(pageRequestArgumentCaptor.getValue().getPageSize()).isEqualTo(LIMIT);
        assertThat(pageRequestArgumentCaptor.getValue().getSort().getOrderFor(SORT_BY).getDirection())
                .isEqualTo(Sort.Direction.DESC);
        assertThat(pageRequestArgumentCaptor.getValue().getSort().getOrderFor(SORT_BY).getProperty())
                .isEqualTo(SORT_BY);

        verify(transformer).transform(foundPokemon);

        verifyNoMoreInteractions(repository, transformer);
    }
}
