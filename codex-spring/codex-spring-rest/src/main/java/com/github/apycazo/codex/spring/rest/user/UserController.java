package com.github.apycazo.codex.spring.rest.user;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "${codex.spring.rest.user.path:user}", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController
{
    // The record storage
    @Getter // for testing
    private Map<Integer, UserInfo> userDirectory = new LinkedHashMap<>();
    // An id generator, in case the id provided on PUT | POST equals '0'
    private AtomicInteger idGenerator = new AtomicInteger(1);

    @Autowired
    private HttpServletRequest servletRequest;
    @Autowired
    private HttpServletResponse servletResponse;

    @GetMapping
    @JsonView(UserInfo.Summary.class) // show only a summary of each entry
    public List<UserInfo> findAll ()
    {
        return userDirectory.values().stream().collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public UserInfo findById (@PathVariable Integer id) throws IOException
    {
        if (userDirectory.containsKey(id)) {
            return userDirectory.get(id);
        }
        else {
            servletResponse.sendError(HttpStatus.NOT_FOUND.value(), "User id '" + id + "' not found");
            return null;
        }
    }

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT }, consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserInfo save (@Valid @RequestBody UserInfo userInfo)
    {
        long now = Instant.now().toEpochMilli();

        // Update record
        if (userDirectory.containsKey(userInfo.getId())) {
            // update
            UserInfo existingValue = userDirectory.get(userInfo.getId());
            userInfo.setCreatedOn(existingValue.getCreatedOn());
            existingValue.setUpdatedOn(now);
            if (userInfo.getRoles() != null ) {
                existingValue.setRoles(userInfo.getRoles());
            }
            if (!userInfo.getUsername().equals(existingValue.getUsername())) {
                existingValue.setUsername(userInfo.getUsername());
            }
            return existingValue;
        }
        // Create record
        else {
            userInfo.setCreatedOn(now);
            userInfo.setUpdatedOn(now);
            if (userInfo.getId() == 0) {
                // set an id
                userInfo.setId(idGenerator.getAndIncrement());
            }
            userDirectory.put(userInfo.getId(), userInfo);
            servletResponse.setStatus(HttpStatus.CREATED.value());
            return userInfo;
        }
    }

    @DeleteMapping
    public void removeAll ()
    {
        userDirectory.clear();
    }

    @DeleteMapping("{id}")
    public void removeById (@PathVariable Integer id)
    {
        if (userDirectory.containsKey(id)) {
            userDirectory.remove(id);
        }
    }

}
