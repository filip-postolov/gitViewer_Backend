package org.filippostolov.gitviewer.dto;

import org.springframework.web.client.RestTemplate;

public class RepositoryInfoDto {
    private String name;
    private String description;
    private String createdAt;
    private String lastUpdated;
    private int commitCount;
    private String lastCommitMessage;
    private String lastCommitDate;
}
