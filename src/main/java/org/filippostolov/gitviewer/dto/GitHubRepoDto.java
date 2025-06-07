package org.filippostolov.gitviewer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;


public record GitHubRepoDto(
    String name,

    @JsonProperty("html_url")
    String htmlUrl,

    @JsonProperty("created_at")
    OffsetDateTime createdAt,

    @JsonProperty("updated_at")
    OffsetDateTime updatedAt,

    @JsonProperty("watchers")
    Integer watchers,

    @JsonProperty("fork")
    Boolean fork,

    @JsonProperty("language")
    String language
) {}
