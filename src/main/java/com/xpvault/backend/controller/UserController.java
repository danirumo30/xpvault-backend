package com.xpvault.backend.controller;

import com.xpvault.backend.dto.AppUserDTO;
import com.xpvault.backend.dto.AppUserDetailsDTO;
import com.xpvault.backend.dto.AppUserTopDTO;
import com.xpvault.backend.dto.MovieDTO;
import com.xpvault.backend.dto.TvSerieDTO;
import com.xpvault.backend.literals.enums.AddResultEnum;
import com.xpvault.backend.facade.UserFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
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
import java.util.Map;

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
        List<AppUserTopDTO> users = userFacade.allUsersBasic();

        if (users.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(NO_USERS_FOUND);
        }

        return ResponseEntity.ok(users);
    }

    @GetMapping(TOP_PATH + "/movies")
    public ResponseEntity<Object> topMovies() {
        List<AppUserTopDTO> users = userFacade.getAllUsersTopMovies();

        if (users.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(NO_USERS_FOUND);
        }

        return ResponseEntity.ok(users);
    }

    @GetMapping(TOP_PATH + "/tv-series")
    public ResponseEntity<Object> topTvSeries() {
        List<AppUserTopDTO> users = userFacade.getAllUsersTopTvSeries();

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

    @GetMapping(PROFILE_PATH + "/{username}/friends")
    public ResponseEntity<Object> getFriends(@PathVariable String username) {
        List<AppUserTopDTO> friends = userFacade.getFriends(username);

        if (friends.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(NO_USERS_FOUND);
        }

        return ResponseEntity.ok(friends);
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
        AddResultEnum result = userFacade.addMovieToUser(username, movieId, language);

        return getAddResultResponse(username, movieId.toString(), MOVIE_NOT_FOUND, SUCCESS_ADD_MOVIE, result, AddResultEnum.MOVIE_NOT_FOUND);
    }

    @PostMapping("/{username}/tv-series/add")
    public ResponseEntity<String> addTvSerieToUser(
            @PathVariable String username,
            @RequestParam Integer tvSerieId,
            @RequestHeader(value = HEADER_ACCEPT_LANGUAGE, defaultValue = HEADER_DEFAULT_LANGUAGE) String language
    ) {
        AddResultEnum result = userFacade.addTvSerieToUser(username, tvSerieId, language);

        return getAddResultResponse(username, tvSerieId.toString(), TV_SERIE_NOT_FOUND, SUCCESS_ADD_TV_SERIE, result, AddResultEnum.TV_SERIE_NOT_FOUND);
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
        AddResultEnum result = userFacade.addFriendToUser(username, friendUsername);

        return getAddResultResponse(username, friendUsername, FRIEND_NOT_FOUND, SUCCESS_ADD_FRIEND, result, AddResultEnum.FRIEND_NOT_FOUND);
    }

    @PostMapping("/save")
    public ResponseEntity<Object> newUser(@Valid @RequestBody AppUserDTO appUserDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        AppUserDTO saved = userFacade.save(appUserDTO);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/{username}/friends/delete")
    public ResponseEntity<String> deleteFriend(
            @PathVariable String username,
            @RequestParam String friendUsername
    ) {
        AddResultEnum result = userFacade.deleteFriendFromUser(username, friendUsername);

        Map<AddResultEnum, ResponseEntity<String>> responses = Map.of(
                AddResultEnum.SUCCESS,
                ResponseEntity.ok("Friend deleted successfully."),
                AddResultEnum.USER_NOT_FOUND,
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(USER_NOT_FOUND + username),
                AddResultEnum.FRIEND_NOT_FOUND,
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(FRIEND_NOT_FOUND + friendUsername)
        );

        return responses.getOrDefault(
                result,
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(UNEXPECTED_ERROR)
        );
    }

    @GetMapping("/{username}/friends/is-friend")
    public ResponseEntity<Object> isFriend(
            @PathVariable String username,
            @RequestParam String friendUsername
    ) {
        boolean result = userFacade.isFriend(username, friendUsername);
        return ResponseEntity.ok(Map.of(
                "username", username,
                "friendUsername", friendUsername,
                "isFriend", result
        ));
    }

    private static @NotNull ResponseEntity<String> getAddResultResponse(
            String username,
            String notFoundParam,
            String notFoundConstant,
            String successResult,
            AddResultEnum result,
            AddResultEnum notFoundResult
    ) {
        Map<AddResultEnum, ResponseEntity<String>> responses =
                Map.of(
                        AddResultEnum.SUCCESS,
                            ResponseEntity.ok(successResult),
                        AddResultEnum.USER_NOT_FOUND,
                            ResponseEntity.status(HttpStatus.NOT_FOUND).body(USER_NOT_FOUND + username),
                        notFoundResult,
                            ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundConstant + notFoundParam),
                        AddResultEnum.ALREADY_EXISTS,
                            ResponseEntity.status(HttpStatus.CONFLICT).body(ALREADY_EXISTS)
                );

        return responses.getOrDefault(
                result,
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(UNEXPECTED_ERROR)
        );
    }

    @PostMapping("/{username}/movies/delete")
    public ResponseEntity<String> deleteMovieFromUser(
            @PathVariable String username,
            @RequestParam Integer movieId
    ) {
        AddResultEnum result = userFacade.deleteMovieFromUser(username, movieId);

        Map<AddResultEnum, ResponseEntity<String>> responses = Map.of(
                AddResultEnum.SUCCESS,
                ResponseEntity.ok("Movie deleted successfully."),
                AddResultEnum.USER_NOT_FOUND,
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(USER_NOT_FOUND + username),
                AddResultEnum.MOVIE_NOT_FOUND,
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(MOVIE_NOT_FOUND + movieId)
        );

        return responses.getOrDefault(
                result,
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(UNEXPECTED_ERROR)
        );
    }

    @PostMapping("/{username}/tv-series/delete")
    public ResponseEntity<String> deleteTvSerieFromUser(
            @PathVariable String username,
            @RequestParam Integer tvSerieId
    ) {
        AddResultEnum result = userFacade.deleteTvSerieFromUser(username, tvSerieId);

        Map<AddResultEnum, ResponseEntity<String>> responses = Map.of(
                AddResultEnum.SUCCESS,
                ResponseEntity.ok("TV Serie deleted successfully."),
                AddResultEnum.USER_NOT_FOUND,
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(USER_NOT_FOUND + username),
                AddResultEnum.TV_SERIE_NOT_FOUND,
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(TV_SERIE_NOT_FOUND + tvSerieId)
        );

        return responses.getOrDefault(
                result,
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(UNEXPECTED_ERROR)
        );
    }

    @GetMapping("/{username}/movies/is-added")
    public ResponseEntity<Map<String, Object>> isMovieAdded(
            @PathVariable String username,
            @RequestParam Integer movieId
    ) {
        boolean result = userFacade.isMovieAdded(username, movieId);
        return ResponseEntity.ok(Map.of(
                "username", username,
                "movieId", movieId,
                "isMovieAdded", result
        ));
    }

    @GetMapping("/{username}/tv-series/is-added")
    public ResponseEntity<Map<String, Object>> isTvSerieAdded(
            @PathVariable String username,
            @RequestParam Integer tvSerieId
    ) {
        boolean result = userFacade.isTvSerieAdded(username, tvSerieId);
        return ResponseEntity.ok(Map.of(
                "username", username,
                "tvSerieId", tvSerieId,
                "isTvSerieAdded", result
        ));
    }
}
