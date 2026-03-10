package com.test.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.test.entity.dto.CreateNoteRequest;
import com.test.entity.dto.NoteResponse;
import com.test.service.DeviceNoteService;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/devices")
public class DeviceNoteController {
	
	private static final Logger logger =
            LoggerFactory.getLogger(DeviceNoteController.class);

	@Autowired
    private DeviceNoteService service;

    @PostMapping("/{deviceId}/notes")
    public NoteResponse createNote(
            @PathVariable Long deviceId,
            @RequestHeader(value = "X-User", required = false) String user,
            @RequestBody CreateNoteRequest request) {
    	logger.info("Create note request received. deviceId={}, user={}", deviceId, user);
        return service.createNote(deviceId, user, request.getNote());
    }

    @GetMapping("/{deviceId}/notes")
    public List<NoteResponse> getNotes(
            @PathVariable Long deviceId,
            @RequestParam(defaultValue = "20") Integer limit) {
    	logger.info("Fetching notes for deviceId={}, limit={}", deviceId, limit);
        return service.getNotes(deviceId, limit);
    }
}