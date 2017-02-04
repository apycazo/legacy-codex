package com.github.apycazo.codex.spring.rest.persistence.one2many;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@Slf4j
@DataJpaTest(showSql = false)
@RunWith(SpringRunner.class)
public class TeamRepositoryTest
{
    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Before
    public void clearPersistence ()
    {
        teamRepository.deleteAll();
        playerRepository.deleteAll();
    }

    @Test
    public void teamPersistenceChecks ()
    {
        Team team = Team.instance("City Team");

        Player p1 = Player.instance("player one", team);
        Player p2 = Player.instance("player two", team);

        team.getPlayers().add(p1);
        team.getPlayers().add(p2);

        Integer id = teamRepository.save(team).getId();
        // Fetch by id
        Team persisted = teamRepository.findOne(id);

        assertNotNull("Persisted value is null", persisted);
        assertNotNull("Persisted list is null", persisted.getPlayers());
        assertFalse("List has no elements", persisted.getPlayers().isEmpty());
        assertEquals("List does not contain expected elements", 2, persisted.getPlayers().size());

        log.info("[{}] '{}' ('{}' players) ", persisted.getId(), persisted.getName(), persisted.getPlayers().size());
        persisted.getPlayers().forEach(player -> log.info("Player ({}) {}", player.getId(), player.getName()));

        assertEquals(1, teamRepository.count());
        assertEquals(2, playerRepository.count());
    }
}