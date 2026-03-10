package com.test.entity.dto;

import java.time.LocalDateTime;

public class NoteResponse {

	private Long id;
	private Long deviceId;
	private String note;
	private String createdBy;
	private LocalDateTime createdAt;

	public NoteResponse(Long id, Long deviceId, String note, String createdBy, LocalDateTime createdAt) {
		this.id = id;
		this.deviceId = deviceId;
		this.note = note;
		this.createdBy = createdBy;
		this.createdAt = createdAt;
	}

	public Long getId() {
		return id;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public String getNote() {
		return note;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
}