package org.filippostolov.gitviewer.controller;

import lombok.RequiredArgsConstructor;
import org.filippostolov.gitviewer.dto.LanguagesDto;
import org.filippostolov.gitviewer.model.exceptions.LanguageMapEmptyException;
import org.filippostolov.gitviewer.service.LanguageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/languages")
public class LanguageController {
    private final LanguageService languageService;

    @GetMapping("/{username}/{repo}")
    public ResponseEntity<LanguagesDto> getLanguages(@PathVariable String username,
                                                     @PathVariable String repo) {

        try {
            LanguagesDto languages = languageService.getLanguagesForRepo(username, repo);
            return ResponseEntity.ok(languages);
        } catch (LanguageMapEmptyException e) {
            throw new LanguageMapEmptyException(e.getMessage());
        }
    }
}
