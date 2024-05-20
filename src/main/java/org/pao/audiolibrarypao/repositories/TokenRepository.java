package org.pao.audiolibrarypao.repositories;

import org.pao.audiolibrarypao.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {}
