package com.xpvault.backend.controller;

import com.xpvault.backend.dto.AppUserDTO;
import com.xpvault.backend.dto.AppUserDetailsDTO;
import com.xpvault.backend.dto.MovieDTO;
import com.xpvault.backend.dto.TvSerieDTO;
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

    @GetMapping("/top/movies")
    public ResponseEntity<Object> topMovies() {
        List<AppUserDTO> users = userFacade.getAllUsersTopMovies();

        if (users.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(NO_USERS_FOUND);
        }

        return ResponseEntity.ok(users);
    }

    @GetMapping("/top/tv-series")
    public ResponseEntity<Object> topTvSeries() {
        List<AppUserDTO> users = userFacade.getAllUsersTopTvSeries();

        if (users.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(NO_USERS_FOUND);
        }

        return ResponseEntity.ok(users);
    }

    @GetMapping("/profile/{username}/tv-series")
    public ResponseEntity<Object> getUserTvSeries(@PathVariable String username) {
        List<TvSerieDTO> series = userFacade.getTvSeries(username);

        if (series.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(NO_USERS_FOUND);
        }

        return ResponseEntity.ok(series);
    }

    @GetMapping("/profile/{username}/movies")
    public ResponseEntity<Object> getUserMovies(@PathVariable String username) {
        List<MovieDTO> movies = userFacade.getMovies(username);

        if (movies.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(NO_USERS_FOUND);
        }

        return ResponseEntity.ok(movies);
    }

    @GetMapping("/profile/{username}/content")
    public ResponseEntity<Object> getUserContent(@PathVariable String username) {
        AppUserDetailsDTO user = userFacade.findFullUserDetails(username);

        if (user == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("User not found or has no content.");
        }

        return ResponseEntity.ok(user);
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

    @GetMapping("/search")
    public ResponseEntity<Object> searchUserByUsername(
            @RequestParam String username
    ) {
        List<AppUserDTO> users = userFacade.findByUsernameContainsIgnoreCase(username);

        if (users == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(NO_USERS_FOUND);
        }

        return ResponseEntity.ok(users);
    }

    @PostMapping("/{username}/friends/add")
    public ResponseEntity<Void> addFriend(
            @PathVariable String username,
            @RequestParam String friendUsername
    ) {
        userFacade.addFriendToUser(username, friendUsername);
        return ResponseEntity.ok().build();
    }
}
