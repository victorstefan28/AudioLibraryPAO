package org.pao.audiolibrarypao.repositories;

import org.pao.audiolibrarypao.entities.RequestLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestLogRepository extends JpaRepository<RequestLog, Long> {
    Page<RequestLog> findByUsername(String username, Pageable pageable);
}