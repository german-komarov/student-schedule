package com.german.studentschedule;

import com.german.studentschedule.domain.User;
import com.german.studentschedule.repository.RoleRepository;
import com.german.studentschedule.repository.UserRepository;
import com.german.studentschedule.dto.FullUserDto;
import com.german.studentschedule.util.constants.RoleName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class StudentScheduleApplicationTests {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Test
    void contextLoads() {
        RoleName roleName = RoleName.valueOf("hello");
        System.err.println(roleName);
    }


}
