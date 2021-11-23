package com.german.studentschedule;

import com.german.studentschedule.domain.Lesson;
import com.german.studentschedule.domain.Subject;
import com.german.studentschedule.exceptions.NotFoundException;
import com.german.studentschedule.repository.UserRepository;
import com.german.studentschedule.services.LessonService;
import com.german.studentschedule.services.SubjectService;
import com.german.studentschedule.util.constants.RoleName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StudentScheduleApplicationTests {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SubjectService subjectService;
    @Test
    void contextLoads() throws NotFoundException {
        Subject subject = this.subjectService.readByIdWithAllNests(2L);
    }


}
