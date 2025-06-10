package org.filippostolov.gitviewer.controller;

import lombok.AllArgsConstructor;
import org.filippostolov.gitviewer.dto.GitHubUserInfoDto;
import org.filippostolov.gitviewer.dto.RepositoryInfoDto;
import org.filippostolov.gitviewer.service.GitHubService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/github")
public class GithubController {
    private final GitHubService gitHubService;

    @GetMapping("/{username}")
    public ResponseEntity<List<RepositoryInfoDto>> getRepositories(
            @PathVariable String username,
            @RequestParam(defaultValue = "name") String sort) {
        try {
            List<RepositoryInfoDto> repos = gitHubService.getRepositoriesForUser(username, sort);
            return ResponseEntity.ok(repos);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/users/{username}")
    public GitHubUserInfoDto getUserInfo(@PathVariable String username) {
        return gitHubService.getUserProfile(username);
    }
}
