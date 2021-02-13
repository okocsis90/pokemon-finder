package com.alea.pokemonfinder.service;

import com.alea.pokemonfinder.exception.PokemonFinderException;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * Facade for creating and sending HTTP requests.
 */
@Service
@RequiredArgsConstructor
public class HttpFacade {

    private final HttpClient httpClient;

    public InputStream sendRequest(final HttpUriRequest request) {
        try {
            final HttpResponse response = httpClient.execute(request);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return response.getEntity().getContent();
            } else {
                throw new PokemonFinderException(EntityUtils.toString(response.getEntity()));
            }
        } catch (Exception e) {
            throw new PokemonFinderException("Unexpected error during external API call.", e);
        }
    }

    public HttpUriRequest buildGetRequest(final String url) {
        return RequestBuilder.get(url)
                .addHeader(HttpHeaders.ACCEPT, "*/*")
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .build();
    }

}
