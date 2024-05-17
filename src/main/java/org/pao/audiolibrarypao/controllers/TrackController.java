package org.pao.audiolibrarypao.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.pao.audiolibrarypao.entities.Track;
import org.pao.audiolibrarypao.services.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tracks")
public class TrackController {
    @Autowired
    private TrackService trackService;

    @PostMapping
    public Track createTrack(@RequestBody Track track) {
        return trackService.saveTrack(track);
    }

    @GetMapping
    public List<Track> getAllTracks() {
        return trackService.getAllTracks();
    }
}

