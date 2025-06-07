package org.filippostolov.gitviewer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GitHubUserInfoDto(
        String username,

        @JsonProperty("avatar_url")
        String avatarUrl
) {
}
