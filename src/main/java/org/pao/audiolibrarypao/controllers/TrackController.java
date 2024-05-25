package org.pao.audiolibrarypao.controllers;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;
import org.pao.audiolibrarypao.entities.Track;
import org.pao.audiolibrarypao.guards.RequiresAdmin;
import org.pao.audiolibrarypao.guards.RequiresAuthentication;
import org.pao.audiolibrarypao.services.TrackService;
import org.pao.audiolibrarypao.utils.classes.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tracks")
public class TrackController {
    @Autowired private TrackService trackService;

    @PostMapping
    @RequiresAdmin
    @JsonView(View.Internal.class)
    public Track createTrack(@RequestBody @JsonView(View.Public.class) Track track) {
        return trackService.saveTrack(track);
    }

    @GetMapping
    @JsonView(View.Public.class)
    public List<Track> getAllTracks() {
        return trackService.getAllTracks();
    }

    @RequiresAuthentication
    @GetMapping("/search")
    public Page<Track> searchTracks(
            @RequestParam(required = false, defaultValue = "") String artist,
            @RequestParam(required = false, defaultValue = "") String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return trackService.searchTracks(artist, title, pageable);
    }

}
