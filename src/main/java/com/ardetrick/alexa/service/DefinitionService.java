package com.ardetrick.alexa.service;

import com.ardetrick.alexa.model.DictionaryResponse;
import com.ardetrick.alexa.model.RhymeWord;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Drew Tenenbaum on 11/21/2016.
 */
public class DefinitionService {

    public String URL_BASE = "http://api.pearson.com/v2/dictionaries/laad3/entries";

    public DictionaryResponse getDictionaryResponse(String word){
        URI targetUrl = getWebServiceURI(word);
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<DictionaryResponse> entity = restTemplate.getForEntity(targetUrl, DictionaryResponse.class);
            HttpStatus serviceStatus = entity.getStatusCode();
            if (serviceStatus == HttpStatus.OK){
                DictionaryResponse dr = entity.getBody();
                return dr;
            } else {
                return new DictionaryResponse();
            }
        } catch (final HttpClientErrorException e){
            throw e;
        }
    }

    public String getDefinition(String word){
        DictionaryResponse response = getDictionaryResponse(word);
        try {
            return response.getResults().get(0).getSenses().get(0).getDefinition();
        }catch(IndexOutOfBoundsException e){
            return null;
        }
    }

    public String removeTrailingSpacesAndPunctuation(String def){
        def = def.trim();
        while(def.charAt(def.length()-1) == '.')
        {
            def = def.substring(0, def.length()-1);
        }
        return def;
    }


    private URI getWebServiceURI(String word) {
        return UriComponentsBuilder.fromUriString(URL_BASE).queryParam("headword", word).queryParam("limit", "1").build().toUri();
    }

}
