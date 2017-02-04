package com.github.apycazo.codex.spring.rest.persistence.one2many;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer>
{
    // Empty
}
