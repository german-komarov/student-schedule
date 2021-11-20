package com.german.studentschedule.repository;

import com.german.studentschedule.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
