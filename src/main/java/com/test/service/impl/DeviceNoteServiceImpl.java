package com.test.service.impl;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import com.test.entity.DeviceNote;
import com.test.entity.dto.NoteResponse;
import com.test.repository.DeviceNoteRepository;
import com.test.service.DeviceNoteService;

import lombok.RequiredArgsConstructor;

@Service
public class DeviceNoteServiceImpl implements DeviceNoteService {

	private static final Logger logger =
            LoggerFactory.getLogger(DeviceNoteService.class);

    @Autowired
    private DeviceNoteRepository repository;

    @Transactional
    public NoteResponse createNote(Long deviceId, String user, String note) {

        if (user == null || user.isBlank()) {
            logger.warn("Missing X-User header");
            throw new IllegalArgumentException("X-User header is required");
        }

        if (note == null || note.isBlank()) {
            logger.warn("Invalid note content");
            throw new IllegalArgumentException("Note cannot be blank");
        }

        if (note.length() > 1000) {
            logger.warn("Note length exceeded 1000 characters");
            throw new IllegalArgumentException("Note cannot exceed 1000 characters");
        }

        DeviceNote entity = new DeviceNote();

        entity.setDeviceId(deviceId);
        entity.setNote(note);
        entity.setCreatedBy(user);
        entity.setCreatedAt(LocalDateTime.now());

        DeviceNote saved = repository.save(entity);

        logger.info("Note created successfully noteId={}, deviceId={}",
                saved.getId(), deviceId);

        return mapToResponse(saved);
    }

    public List<NoteResponse> getNotes(Long deviceId, Integer limit) {

        if (limit == null) {
            limit = 20;
        }

        if (limit < 1 || limit > 100) {
            logger.warn("Invalid limit value: {}", limit);
            throw new IllegalArgumentException("Limit must be between 1 and 100");
        }

        Pageable pageable = PageRequest.of(0, limit);

        List<DeviceNote> notes =
                repository.findByDeviceIdOrderByCreatedAtDesc(deviceId, pageable);

        return notes.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private NoteResponse mapToResponse(DeviceNote note) {
        return new NoteResponse(
                note.getId(),
                note.getDeviceId(),
                note.getNote(),
                note.getCreatedBy(),
                note.getCreatedAt()
        );
    }

}
