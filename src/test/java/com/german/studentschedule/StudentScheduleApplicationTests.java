package com.german.studentschedule;

import com.german.studentschedule.repository.UserRepository;
import com.german.studentschedule.util.constants.RoleName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StudentScheduleApplicationTests {

    @Autowired
    private UserRepository userRepository;
    @Test
    void contextLoads() {
        RoleName roleName = RoleName.valueOf("hello");
        System.err.println(roleName);
    }


}
