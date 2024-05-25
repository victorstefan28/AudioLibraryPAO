package org.pao.audiolibrarypao.repositories;

import org.pao.audiolibrarypao.entities.Track;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackRepository extends JpaRepository<Track, Long> {
    Page<Track> findByArtistContainingAndTitleContaining(String artist, String title, Pageable pageable);

}
