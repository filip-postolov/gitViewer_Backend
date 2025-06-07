package org.filippostolov.gitviewer.service;

import org.filippostolov.gitviewer.dto.GitHubUserInfoDto;
import org.filippostolov.gitviewer.dto.RepositoryInfoDto;

import java.util.List;

public interface GitHubService {

    List<RepositoryInfoDto> getRepositoriesForUser(String username);

    GitHubUserInfoDto getUserProfile(String username);

}
