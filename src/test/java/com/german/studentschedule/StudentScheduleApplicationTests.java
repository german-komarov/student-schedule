package com.german.studentschedule;

import com.german.studentschedule.domain.User;
import com.german.studentschedule.repository.RoleRepository;
import com.german.studentschedule.repository.UserRepository;
import com.german.studentschedule.util.dto.UserDto;
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
    @Transactional
    void contextLoads() {
        List<User> users = this.userRepository.findAllCustom();
        List<UserDto> dtos = new ArrayList<>();
        for(User u : users) {
            UserDto dto = new UserDto(u);
            dtos.add(dto);
        }
        System.err.println("OK");
    }


}
