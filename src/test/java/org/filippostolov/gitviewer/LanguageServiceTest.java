package org.filippostolov.gitviewer;

import org.filippostolov.gitviewer.dto.LanguagesDto;
import org.filippostolov.gitviewer.service.LanguageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class LanguageServiceTest {

    @Autowired
    private LanguageService languageService;

    @Test
    public void testLanguageService() {
        String username = "filip-postolov";
        String repo = "gitViewer_Backend";

        LanguagesDto languagesDto = languageService.getLanguagesForRepo(username, repo);

        assertNotNull(languagesDto, "Languages should not be null");

        languagesDto.languages().forEach((language, lines) ->
                System.out.println("Language: " + language + " Lines: " + lines));
    }
}
