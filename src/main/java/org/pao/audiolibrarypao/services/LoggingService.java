package org.pao.audiolibrarypao.services;

import org.pao.audiolibrarypao.entities.RequestLog;
import org.pao.audiolibrarypao.repositories.RequestLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service class for managing request logs.
 */
@Service
public class LoggingService {

    private final RequestLogRepository requestLogRepository;

    /**
     * Constructs a new LoggingService with the specified repository.
     *
     * @param requestLogRepository the repository for request logs
     */
    @Autowired
    public LoggingService(RequestLogRepository requestLogRepository) {
        this.requestLogRepository = requestLogRepository;
    }

    /**
     * Logs a request with the specified details.
     *
     * @param requestURI the URI of the request
     * @param user the username of the user making the request
     * @param responseCode the HTTP response code
     * @param body the body of the request
     * @param payload the payload of the request
     */
    public void logRequest(String requestURI, String user, int responseCode, String body, String payload) {
        RequestLog log = new RequestLog();
        log.setRequestURI(requestURI);
        log.setUsername(user);
        log.setResponseCode(responseCode);
        log.setBody(body);
        log.setPayload(payload);
        requestLogRepository.save(log);
    }

    /**
     * Retrieves a paginated list of request logs, optionally filtered by username.
     *
     * @param pageable the pagination information
     * @param username the username to filter logs by (optional)
     * @return a paginated list of request logs
     */
    public Page<RequestLog> getAuditLogs(Pageable pageable, String username) {
        if (!username.isEmpty()) {
            return requestLogRepository.findByUsername(username, pageable);
        }
        return requestLogRepository.findAll(pageable);
    }
}
