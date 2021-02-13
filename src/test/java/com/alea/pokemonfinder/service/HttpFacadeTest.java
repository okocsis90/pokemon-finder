package com.alea.pokemonfinder.service;

import com.alea.pokemonfinder.exception.PokemonFinderException;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
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
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class HttpFacadeTest {

    private static final String URL = "url";
    private static final String GET_METHOD = "GET";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private HttpClient httpClient;

    @InjectMocks
    private HttpFacade underTest;

    @Test
    public void testSendRequestWhenExecuteThrowsErrorThenThrowError() throws IOException {
        expectedException.expect(PokemonFinderException.class);
        expectedException.expectMessage("Unexpected error during external API call.");

        final HttpUriRequest request = mock(HttpUriRequest.class);

        when(httpClient.execute(request)).thenThrow(new IOException());

        underTest.sendRequest(request);
    }

    @Test
    public void testSendRequestWhenResponseNotOkThenThrowException() throws IOException {
        expectedException.expect(PokemonFinderException.class);

        final HttpUriRequest request = mock(HttpUriRequest.class);
        final HttpResponse response = mock(HttpResponse.class);
        final StatusLine statusLine = mock(StatusLine.class);
        final HttpEntity entity = mock(HttpEntity.class);

        when(httpClient.execute(request)).thenReturn(response);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(statusLine.getStatusCode()).thenReturn(HttpStatus.SC_BAD_REQUEST);
        when(response.getEntity()).thenReturn(entity);

        underTest.sendRequest(request);
    }

    @Test
    public void testSendRequestWhenHappyPathThenReturnInputStream() throws IOException {
        final HttpUriRequest request = mock(HttpUriRequest.class);
        final HttpResponse response = mock(HttpResponse.class);
        final StatusLine statusLine = mock(StatusLine.class);
        final HttpEntity entity = mock(HttpEntity.class);
        final InputStream content = mock(InputStream.class);

        when(httpClient.execute(request)).thenReturn(response);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(statusLine.getStatusCode()).thenReturn(HttpStatus.SC_OK);
        when(response.getEntity()).thenReturn(entity);
        when(entity.getContent()).thenReturn(content);

        final InputStream actual = underTest.sendRequest(request);

        assertThat(actual).isEqualTo(content);

        verify(httpClient).execute(request);
        verifyNoMoreInteractions(httpClient);
    }

    @Test
    public void testBuildGetRequestWhenCalledThenBuildGetRequest() {
        final HttpUriRequest actual = underTest.buildGetRequest(URL);

        assertThat(actual.getMethod()).isEqualTo(GET_METHOD);
        assertThat(actual.getURI().toString()).isEqualTo(URL);
    }
}
