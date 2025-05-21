package com.xpvault.backend.converter;

import com.ibasco.agql.protocols.valve.steam.webapi.pojos.SteamPlayerProfile;
import com.xpvault.backend.dto.OwnedSteamGameDTO;
import com.xpvault.backend.dto.SteamUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SteamPlayerProfileToSteamUserDTOConverter {

    public SteamUserDTO convert(SteamPlayerProfile source, Long totalTimePlayed, List<OwnedSteamGameDTO> ownedGames) {
        return new SteamUserDTO(
                Long.valueOf(source.getSteamId()),
                source.getName(),
                source.getAvatarFullUrl(),
                source.getProfileUrl(),
                totalTimePlayed,
                ownedGames
        );
    }
}
