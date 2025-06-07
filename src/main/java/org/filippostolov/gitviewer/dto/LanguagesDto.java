package org.filippostolov.gitviewer.dto;

import java.util.Map;

public record LanguagesDto(
        Map<String, Integer> languages
) {}
