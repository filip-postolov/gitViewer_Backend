package org.filippostolov.gitviewer.controller;

import lombok.AllArgsConstructor;
import org.filippostolov.gitviewer.model.GitHubRepositories;
import org.filippostolov.gitviewer.service.GitHubService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/github")
public class GithubController {
    private final GitHubService gitHubService;

    @GetMapping("/{username}")
    public ResponseEntity<List<GitHubRepositories>> getRepositories(@PathVariable String username) {
        try {
            List<GitHubRepositories> repos = gitHubService.getRepositoriesForUser(username);
            return ResponseEntity.ok(repos);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
