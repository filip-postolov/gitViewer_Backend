package org.filippostolov.gitviewer.service;

import lombok.RequiredArgsConstructor;
import org.filippostolov.gitviewer.dto.GitHubRepoDto;
import org.filippostolov.gitviewer.dto.GitHubUserInfoDto;
import org.filippostolov.gitviewer.dto.RepositoryInfoDto;
import org.filippostolov.gitviewer.model.exceptions.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class GitHubService {
    private final RestTemplate restTemplate;

    private final Map<String, CachedData> cache = new ConcurrentHashMap<>();

    private static final long CACHE_DURATION = 10 * 60;

    public List<RepositoryInfoDto> getRepositoriesForUser(String username) {
        CachedData cached = cache.get(username);
        Instant now = Instant.now();

        if (cached != null && now.isBefore(cached.timestamp.plusSeconds(CACHE_DURATION))) {
            return cached.repos;
        }


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
        List<RepositoryInfoDto> repos = rawRepos.stream()
                .map(repo -> new RepositoryInfoDto(
                        repo.name(),
                        repo.htmlUrl(),
                        repo.createdAt(),
                        repo.updatedAt(),
                        repo.watchers(),
                        repo.fork()
                ))
                .collect(Collectors.toList());

        cache.put(username, new CachedData(repos, now));

        return repos;
    }

    public GitHubUserInfoDto getUserProfile(String username) {
        String url = "https://api.github.com/users/" + username;
        try {
            return restTemplate.getForObject(url, GitHubUserInfoDto.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new UserNotFoundException("Github user not found: " + username);
        }
        catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }

    private record CachedData(List<RepositoryInfoDto> repos, Instant timestamp) {
    }

}
