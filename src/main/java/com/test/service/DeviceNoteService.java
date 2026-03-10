package com.test.service;

import java.util.List;

import com.test.entity.dto.NoteResponse;

public interface DeviceNoteService {

	NoteResponse createNote(Long deviceId, String user, String note);
	
	List<NoteResponse> getNotes(Long deviceId, Integer limit);
	
}
