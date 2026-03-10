package com.test.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.entity.DeviceNote;

@Repository
public interface DeviceNoteRepository extends JpaRepository<DeviceNote, Long> {
	List<DeviceNote> findByDeviceIdOrderByCreatedAtDesc(Long deviceId, Pageable pageable);
}
