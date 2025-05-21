package com.xpvault.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "steam_user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SteamUserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "steam_id", unique = true, nullable = false)
    private Long steamId;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "profile_url")
    private String profileUrl;

    @Column(name = "total_time_played")
    private Long totalTimePlayed;

    @OneToOne(mappedBy = "steamUser")
    private AppUserModel appUser;

}
