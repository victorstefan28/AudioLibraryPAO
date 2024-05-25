package org.pao.audiolibrarypao.controllers;

import java.util.List;
import org.pao.audiolibrarypao.entities.Playlist;
import org.pao.audiolibrarypao.entities.User;
import org.pao.audiolibrarypao.guards.RequiresAdmin;
import org.pao.audiolibrarypao.guards.RequiresAuthentication;
import org.pao.audiolibrarypao.services.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/playlists")
public class PlaylistController {
    @Autowired private PlaylistService playlistService;

    @GetMapping
    @RequiresAuthentication
    public Page<Playlist> getPlaylists(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return playlistService.listPlaylists(PageRequest.of(page, size));
    }

    @PostMapping

    public Playlist createPlaylist(
            @RequestParam String name,
            @RequestParam String description,
            @AuthenticationPrincipal User user) {

        return playlistService.createPlaylist(name, description, user);
    }
    @RequiresAuthentication
    @PostMapping("/{playlistId}/tracks/{trackId}")
    public Playlist addTrackToPlaylist(@PathVariable Long playlistId, @PathVariable Long trackId, @AuthenticationPrincipal User user) {
        return playlistService.addTrackToPlaylist(playlistId, trackId, user);
    }
    @RequiresAuthentication
    @PostMapping("/{playlistId}/tracks")
    public Playlist addTracksToPlaylist(
            @PathVariable Long playlistId, @RequestBody List<Long> trackIds, @AuthenticationPrincipal User user) {
        return playlistService.addTracksToPlaylist(playlistId, trackIds, user);
    }
}
