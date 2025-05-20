package com.xpvault.backend.config;

import com.ibasco.agql.protocols.valve.steam.webapi.SteamWebApiClient;
import com.ibasco.agql.protocols.valve.steam.webapi.interfaces.SteamApps;
import com.ibasco.agql.protocols.valve.steam.webapi.interfaces.SteamNews;
import com.ibasco.agql.protocols.valve.steam.webapi.interfaces.SteamPlayerService;
import com.ibasco.agql.protocols.valve.steam.webapi.interfaces.SteamStorefront;
import com.ibasco.agql.protocols.valve.steam.webapi.interfaces.SteamUser;
import com.xpvault.backend.dao.UserDAO;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovieLists;
import info.movito.themoviedbapi.TmdbMovies;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    @Value("${steam.api.key}")
    private String steamApiKey;

    @Value("${tmdb.api.key}")
    private String tmdbApiKey;

    private final UserDAO userDAO;

    @Bean
    UserDetailsService userDetailsService() {
        return username -> userDAO.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SteamWebApiClient steamWebApiClient() {
        return new SteamWebApiClient(steamApiKey);
    }

    @Bean
    public SteamStorefront storeFront(SteamWebApiClient client) {
        return new SteamStorefront(client);
    }

    @Bean
    public SteamNews steamNews(SteamWebApiClient client) {
        return new SteamNews(client);
    }

    @Bean
    public SteamPlayerService playerService(SteamWebApiClient client) {
        return new SteamPlayerService(client);
    }

    @Bean
    public SteamApps steamApps(SteamWebApiClient client) {
        return new SteamApps(client);
    }

    @Bean
    public SteamUser steamUser(SteamWebApiClient client) {
        return new SteamUser(client);
    }

    @Bean
    public TmdbApi tmdbClient() {
        return new TmdbApi(tmdbApiKey);
    }

    @Bean
    public TmdbMovies tmdbMovies(TmdbApi tmdbApi) {
        return tmdbApi.getMovies();
    }

    @Bean
    public TmdbMovieLists tmdbMoviesList(TmdbApi tmdbApi) {
        return tmdbApi.getMovieLists();
    }
}
