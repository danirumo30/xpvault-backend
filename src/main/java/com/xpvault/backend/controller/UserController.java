package com.xpvault.backend.controller;

import com.xpvault.backend.dto.AppUserDTO;
import com.xpvault.backend.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    public static final String NO_USERS_FOUND = "No users found.";
    private final UserFacade userFacade;

    @GetMapping("/me")
    public ResponseEntity<AppUserDTO> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        AppUserDTO user = userFacade.findByUsername(username);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/all")
    public ResponseEntity<Object> allUsers() {
        List<AppUserDTO> users = userFacade.allUsers();

        if (users.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(NO_USERS_FOUND);
        }

        return ResponseEntity.ok(users);
    }

    @GetMapping("/top/time/movies")
    public ResponseEntity<Object> topMovies() {
        List<AppUserDTO> users = userFacade.getAllUsersTopTimeMovies();

        if (users.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(NO_USERS_FOUND);
        }

        return ResponseEntity.ok(users);
    }

    @GetMapping("/top/time/tv-series")
    public ResponseEntity<Object> topTvSeries() {
        List<AppUserDTO> users = userFacade.getAllUsersTopTimeTvSeries();

        if (users.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(NO_USERS_FOUND);
        }

        return ResponseEntity.ok(users);
    }

    @PostMapping("/{username}/movies/add")
    public ResponseEntity<Void> addMovieToUser(
            @PathVariable String username,
            @RequestParam Integer movieId,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language
    ) {
        userFacade.addMovieToUser(username, movieId, language);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{username}/tv-series/add")
    public ResponseEntity<Void> addTvSerieToUser(
            @PathVariable String username,
            @RequestParam Integer tvSerieId,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language
    ) {
        userFacade.addTvSerieToUser(username, tvSerieId, language);
        return ResponseEntity.ok().build();
    }

}
