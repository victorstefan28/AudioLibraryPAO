package org.pao.audiolibrarypao.repositories;

import org.pao.audiolibrarypao.entities.Token;
import org.pao.audiolibrarypao.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
}