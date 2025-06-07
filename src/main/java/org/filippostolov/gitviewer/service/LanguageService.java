package org.filippostolov.gitviewer.service;

import org.filippostolov.gitviewer.dto.LanguagesDto;


public interface LanguageService {

    LanguagesDto getLanguagesForRepo(String username, String repo);
}
