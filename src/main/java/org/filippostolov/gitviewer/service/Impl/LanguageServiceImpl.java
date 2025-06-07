package org.filippostolov.gitviewer.service.Impl;

import lombok.RequiredArgsConstructor;
import org.filippostolov.gitviewer.dto.LanguagesDto;

import org.filippostolov.gitviewer.model.exceptions.LanguageMapEmptyException;
import org.filippostolov.gitviewer.service.LanguageService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.Map;

@Service
@RequiredArgsConstructor
public class LanguageServiceImpl implements LanguageService {

    private final RestTemplate restTemplate;

    @Override
    public LanguagesDto getLanguagesForRepo(String username, String repo) {
        String url = "https://api.github.com/repos/" + username + "/" + repo + "/languages";

        ResponseEntity<Map<String, Integer>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );

        Map<String, Integer> languageMap = response.getBody();
        if (languageMap == null) {
            throw new LanguageMapEmptyException("No languages for " + username + "/" + repo);
        }

        return new LanguagesDto(languageMap);
    }
}
