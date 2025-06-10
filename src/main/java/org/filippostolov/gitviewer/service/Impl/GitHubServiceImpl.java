package org.filippostolov.gitviewer.service.Impl;

import lombok.RequiredArgsConstructor;
import org.filippostolov.gitviewer.dto.GitHubRepoDto;
import org.filippostolov.gitviewer.dto.GitHubUserInfoDto;
import org.filippostolov.gitviewer.dto.RepositoryInfoDto;
import org.filippostolov.gitviewer.model.enumerations.SortOption;
import org.filippostolov.gitviewer.model.exceptions.*;
import org.filippostolov.gitviewer.service.GitHubService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class GitHubServiceImpl implements GitHubService {
    private final RestTemplate restTemplate;


    @Override
    public List<RepositoryInfoDto> getRepositoriesForUser(String username, String sortParam) {



        String url = "https://api.github.com/users/" + username + "/repos";

        List<GitHubRepoDto> rawRepos = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<GitHubRepoDto>>() {}
        ).getBody();

        if(rawRepos==null) {
            throw new ReposNotFoundException(username);
        }

        SortOption sortOption = SortOption.from(sortParam);
        Comparator<GitHubRepoDto> comparator = sortComparators.getOrDefault(sortOption, Comparator.comparing(GitHubRepoDto::name));


        return rawRepos.stream()
                .sorted(comparator)
                .map(repo -> new RepositoryInfoDto(
                        repo.name(),
                        repo.htmlUrl(),
                        repo.createdAt(),
                        repo.updatedAt(),
                        repo.watchers(),
                        repo.fork(),
                        repo.language()
                ))
                .collect(Collectors.toList());

    }


    @Override
    public GitHubUserInfoDto getUserProfile(String username) {
        String url = "https://api.github.com/users/" + username;
        try {
            return restTemplate.getForObject(url, GitHubUserInfoDto.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new UserNotFoundException("Github user " + username + " not found" );
        }
        catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }



    private final Map<SortOption, Comparator<GitHubRepoDto>> sortComparators = Map.of(
            SortOption.CREATED, Comparator.comparing(GitHubRepoDto::createdAt).reversed(),
            SortOption.UPDATED, Comparator.comparing(GitHubRepoDto::updatedAt).reversed(),
            SortOption.LANGUAGE, Comparator.comparing(repo -> Optional.ofNullable(repo.language()).orElse("")),
            SortOption.NAME, Comparator.comparing(GitHubRepoDto::name),
            SortOption.WATCHERS, Comparator.comparing(GitHubRepoDto::watchers, Comparator.nullsLast(Integer::compareTo)).reversed()
    );


}
