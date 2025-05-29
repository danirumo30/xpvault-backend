package com.xpvault.backend.literals.constants;

public final class AppConstants {

    private AppConstants() {}

    // -------------------------------------------
    // COMMON MESSAGES
    // -------------------------------------------
    public static final String HAS_NO_CONTENT = "Has no content";
    public static final String NO_USERS_FOUND = "No users found.";
    public static final String NO_OWNED_GAMES = "No owned games.";
    public static final String NO_STEAM_DETAILS = "No details found for Steam ID: ";
    public static final String NO_FEATURED_GAME = "No featured games.";
    public static final String NO_STEAM_NEWS = "No news found for Steam ID: ";
    public static final String NO_STEAM_APPS = "No steam apps found.";
    public static final String NO_GAMES_FOR_TITLE = "No games for this title.";
    public static final String NO_GAMES_FOR_GENRE = "No games for this genre.";
    public static final String NO_GAMES_FOR_PAGE = "No Steam apps found for this page.";
    public static final String USER_NOT_FOUND = "User not found with username: ";
    public static final String FRIEND_NOT_FOUND = "Friend not found with username: ";
    public static final String MOVIE_NOT_FOUND = "Movie not found with ID: ";
    public static final String MOVIE_TITLE_NOT_FOUND = "No movies found with title: ";
    public static final String MOVIE_GENRE_NOT_FOUND = "No movies found with genre: ";
    public static final String POPULAR_MOVIE_NOT_FOUND = "No popular movies found.";
    public static final String TOP_RATED_MOVIE_NOT_FOUND = "No top-rated movies found.";
    public static final String UPCOMING_MOVIE_NOT_FOUND = "No upcoming movies found.";
    public static final String TV_SERIE_NOT_FOUND = "TV Series not found with ID: %d";
    public static final String TV_SERIE_TITLE_NOT_FOUND = "No TV Series found with title: ";
    public static final String TV_SERIE_GENRE_NOT_FOUND = "No TV Series found with genre: ";
    public static final String POPULAR_TV_SERIE_NOT_FOUND = "No popular TV Series found.";
    public static final String TOP_RATED_TV_SERIE_NOT_FOUND = "No top-rated TV Series found.";
    public static final String STEAM_ID_NOT_FOUND_FOR_USERNAME = "SteamID not found for username: ";
    public static final String USERNAME_NOT_FOUND_FOR_STEAM_ID = "Username could not be resolved by ID: ";
    public static final String STEAM_PROFILE_NOT_FOUND = "Steam profile not found for SteamID: ";
    public static final String ALREADY_EXISTS = "%s already added to user.";
    public static final String SUCCESS_ADD_MOVIE = "Movie added successfully.";
    public static final String SUCCESS_ADD_TV_SERIE = "TV Series added successfully.";
    public static final String SUCCESS_ADD_FRIEND = "Friend added successfully.";
    public static final String SUCCESS_VERIFY = "Account verified successfully.";
    public static final String SUCCESS_RESEND = "Account resend successfully.";
    public static final String SUCCESS_GAME_DELETE = "Game deleted successfully!";
    public static final String UNEXPECTED_ERROR = "Unexpected error";
    public static final String ERROR_RESOLVING_STEAM_ID = "Error resolving SteamID: ";
    public static final String ERROR_RESOLVING_STEAM_USERNAME = "Error resolving Steam username: ";

    // -------------------------------------------
    // PATHS / URLS
    // -------------------------------------------¡
    public static final String AUTH_PATH = "/auth";
    public static final String USER_PATH = "/users";
    public static final String MOVIES_PATH = "/movies";
    public static final String TV_SERIES_PATH = "/tv-series";
    public static final String POPULAR_PATH = "/popular";
    public static final String TOP_RATED_PATH = "/top-rated";
    public static final String UPCOMING_PATH = "/upcoming";
    public static final String GAME_PATH = "/game";
    public static final String STEAM_USER_PATH = "/steam-user";
    public static final String STEAM_PATH = "/steam";
    public static final String RESOLVE_PATH = "/resolve";
    public static final String LOGIN_PATH = "/login";
    public static final String REGISTER_PATH = "/signup";
    public static final String VERIFY_PATH = "/verify";
    public static final String RESEND_PATH = "/resend";
    public static final String PROFILE_PATH = "/profile";
    public static final String TOP_PATH = "/top";
    public static final String STEAM_AUTH_PATH = "/steam-auth";
    public static final String STEAM_API_URL = "https://steamspy.com/api.php?request=all";

    // -------------------------------------------
    // HEADER KEYS
    // -------------------------------------------
    public static final String HEADER_ACCEPT_LANGUAGE = "Accept-Language";
    public static final String HEADER_DEFAULT_LANGUAGE = "en";

    // -------------------------------------------
    // CONFIGS
    // -------------------------------------------
    public static final String ADMIN_MAIL = "xpvault.team@gmail.com";
}

