package org.filippostolov.gitviewer.dto;

import java.time.OffsetDateTime;


public record RepositoryInfoDto (
     String name,
     String htmlUrl,
     OffsetDateTime createdAt,
     OffsetDateTime updatedAt,
     Integer watchers,
     Boolean fork,
     String language
) {}
