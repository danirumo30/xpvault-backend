package com.xpvault.backend.controller;

import com.xpvault.backend.dto.AppUserDTO;
import com.xpvault.backend.dto.AppUserDetailsDTO;
import com.xpvault.backend.dto.MovieDTO;
import com.xpvault.backend.dto.TvSerieDTO;
import com.xpvault.backend.literals.enums.AddFriendResultEnum;
import com.xpvault.backend.literals.enums.AddMediaResultEnum;
import com.xpvault.backend.facade.UserFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.xpvault.backend.literals.constants.AppConstants.*;

@RestController
@RequestMapping(USER_PATH)
@RequiredArgsConstructor
public class UserController {

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

    @GetMapping(TOP_PATH + "/movies")
    public ResponseEntity<Object> topMovies() {
        List<AppUserDTO> users = userFacade.getAllUsersTopMovies();

        if (users.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(NO_USERS_FOUND);
        }

        return ResponseEntity.ok(users);
    }

    @GetMapping(TOP_PATH + "/tv-series")
    public ResponseEntity<Object> topTvSeries() {
        List<AppUserDTO> users = userFacade.getAllUsersTopTvSeries();

        if (users.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(NO_USERS_FOUND);
        }

        return ResponseEntity.ok(users);
    }

    @GetMapping(PROFILE_PATH + "/{username}/tv-series")
    public ResponseEntity<Object> getUserTvSeries(@PathVariable String username) {
        List<TvSerieDTO> series = userFacade.getTvSeries(username);

        if (series.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(NO_USERS_FOUND);
        }

        return ResponseEntity.ok(series);
    }

    @GetMapping(PROFILE_PATH + "/{username}/movies")
    public ResponseEntity<Object> getUserMovies(@PathVariable String username) {
        List<MovieDTO> movies = userFacade.getMovies(username);

        if (movies.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(NO_USERS_FOUND);
        }

        return ResponseEntity.ok(movies);
    }

    @GetMapping(PROFILE_PATH + "/{username}/content")
    public ResponseEntity<Object> getUserContent(@PathVariable String username) {
        AppUserDetailsDTO user = userFacade.findFullUserDetails(username);

        if (user == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(USER_NOT_FOUND + username + " OR " + HAS_NO_CONTENT);
        }

        return ResponseEntity.ok(user);
    }

    @PostMapping("/{username}/movies/add")
    public ResponseEntity<String> addMovieToUser(
            @PathVariable String username,
            @RequestParam Integer movieId,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language
    ) {
        AddMediaResultEnum result = userFacade.addMovieToUser(username, movieId, language);

        return switch (result) {
            case SUCCESS ->
                    ResponseEntity.ok(SUCCESS_ADD_MOVIE);
            case USER_NOT_FOUND ->
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(USER_NOT_FOUND + username);
            case MOVIE_NOT_FOUND ->
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(MOVIE_NOT_FOUND + movieId);
            case ALREADY_EXISTS ->
                    ResponseEntity.status(HttpStatus.CONFLICT).body(ALREADY_EXISTS);
            default ->
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(UNEXPECTED_ERROR);
        };
    }

    @PostMapping("/{username}/tv-series/add")
    public ResponseEntity<String> addTvSerieToUser(
            @PathVariable String username,
            @RequestParam Integer tvSerieId,
            @RequestHeader(value = HEADER_ACCEPT_LANGUAGE, defaultValue = HEADER_DEFAULT_LANGUAGE) String language
    ) {
        AddMediaResultEnum result = userFacade.addTvSerieToUser(username, tvSerieId, language);

        return switch (result) {
            case SUCCESS ->
                    ResponseEntity.ok(SUCCESS_ADD_TV_SERIE);
            case USER_NOT_FOUND ->
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(USER_NOT_FOUND + username);
            case TV_SERIE_NOT_FOUND ->
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(TV_SERIE_NOT_FOUND + tvSerieId);
            case ALREADY_EXISTS ->
                    ResponseEntity.status(HttpStatus.CONFLICT).body(ALREADY_EXISTS);
            default ->
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(UNEXPECTED_ERROR);
        };
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
    public ResponseEntity<String> addFriend(
            @PathVariable String username,
            @RequestParam String friendUsername
    ) {
        AddFriendResultEnum result = userFacade.addFriendToUser(username, friendUsername);

        return switch (result) {
            case SUCCESS ->
                    ResponseEntity.ok(SUCCESS_ADD_FRIEND);
            case USER_NOT_FOUND ->
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(USER_NOT_FOUND + username);
            case FRIEND_NOT_FOUND ->
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(FRIEND_NOT_FOUND + friendUsername);
            case ALREADY_FRIENDS ->
                    ResponseEntity.status(HttpStatus.CONFLICT).body(ALREADY_EXISTS);
        };
    }

    @PostMapping("/save")
    public ResponseEntity<Object> newUser(@Valid @RequestBody AppUserDTO appUserDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        AppUserDTO saved = userFacade.save(appUserDTO);
        return ResponseEntity.ok(saved);
    }
}
