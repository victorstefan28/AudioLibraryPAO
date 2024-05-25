package org.pao.audiolibrarypao.controllers;

import org.pao.audiolibrarypao.entities.RequestLog;
import org.pao.audiolibrarypao.guards.RequiresAdmin;
import org.pao.audiolibrarypao.services.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController

public class AuditController {

    private final LoggingService loggingService;

    @Autowired
    public AuditController(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    @RequiresAdmin
    @GetMapping("/audit")
    public Page<RequestLog> getAuditLogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "", required = false) String username){

        Pageable pageable = PageRequest.of(page, size);
        return loggingService.getAuditLogs(pageable, username);
    }
}