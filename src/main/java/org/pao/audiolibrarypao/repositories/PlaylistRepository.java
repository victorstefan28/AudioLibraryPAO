package org.pao.audiolibrarypao.repositories;

import org.pao.audiolibrarypao.entities.Playlist;
import org.pao.audiolibrarypao.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    boolean existsByNameAndUser(String name, User user);
}
