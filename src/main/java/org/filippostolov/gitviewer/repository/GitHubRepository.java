package org.filippostolov.gitviewer.repository;

import org.filippostolov.gitviewer.model.GitHubRepositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GitHubRepository extends JpaRepository<GitHubRepositories, Long> {
}
