package org.filippostolov.gitviewer;



import org.filippostolov.gitviewer.dto.RepositoryInfoDto;
import org.filippostolov.gitviewer.service.GitHubService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class GitHubServiceTest {

    @Autowired
    private GitHubService gitHubService;

    @Test
    public void testGetReposForUser() {
        String username = "filip-postolov";

        List<RepositoryInfoDto> repos = gitHubService.getRepositoriesForUser(username);

        assertNotNull(repos, "Repository list should not be null");
        assertFalse(repos.isEmpty(), "Repository list should not be empty");

        repos.forEach(repo -> {
            assertNotNull(repo.name(), "Repo name should not be null");
            System.out.println("Repository: " + repo.name());

            assertNotNull(repo.htmlUrl(), "URL should not be null");
            System.out.println("  URL: " + repo.htmlUrl());

            assertNotNull(repo.createdAt(), "Created date should not be null");
            System.out.println("  Created At: " + repo.createdAt());

            assertNotNull(repo.updatedAt(), "Updated at should not be null");
            System.out.println("  Last Updated: " + repo.updatedAt());

            assertNotNull(repo.watchers(), "Watchers should not be null");
            System.out.println("  Watchers: " + repo.watchers());

            assertNotNull(repo.fork(), "isFork should not be null");
            System.out.println("  is Fork: " + repo.fork());

            System.out.println("-------------------------");
        });

    }
}
