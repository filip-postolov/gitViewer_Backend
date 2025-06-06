package org.filippostolov.gitviewer.service;


import lombok.RequiredArgsConstructor;
import org.filippostolov.gitviewer.model.GitHubRepositories;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GitHubService {
    private final RestTemplate restTemplate;

    public List<GitHubRepositories> getRepositoriesForUser(String username) {
        String url = "https://api.github.com/users/" + username + "/repos";


        List<Map<String, Object>> rawRepos = restTemplate.getForObject(url, List.class);

        List<GitHubRepositories> repoList = new ArrayList<>();

        if (rawRepos != null) {
            for (Map<String, Object> repo : rawRepos) {
                GitHubRepositories repoModel = new GitHubRepositories();
                repoModel.setName((String) repo.get("name"));
                repoModel.setDescription((String) repo.get("description"));
                repoModel.setCreatedAt((String) repo.get("created_at"));
                repoModel.setLastUpdated((String) repo.get("updated_at"));
                repoModel.setHtmlUrl((String) repo.get("html_url"));
                repoList.add(repoModel);
            }
        }

        return repoList;
    }


}
