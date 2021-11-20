package com.german.studentschedule;

import com.german.studentschedule.repository.RoleRepository;
import com.german.studentschedule.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class StudentScheduleApplicationTests {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Test
    @Transactional
    void contextLoads() {
//        Role role = this.authorityRepository.saveAndFlush(new Role(RoleName.HELLO));
//        Student student = new Student();
//        student.setEmail("sdfgh");
//        student.setEnabled(true);
//        student.setPassword("sdfgfs");
//        student.setName("boy");
//        student.setRoles(Collections.singleton(role));
//        userRepository.saveAndFlush(student);
//        Professor professor = new Professor();
//        professor.setExperience("sdfrgthy");
//        professor.setEmail("werty");
//        professor.setRoles(Collections.singleton(role));
//        professor.setEnabled(true);
//        professor.setPassword("sdfghj");
//        userRepository.saveAndFlush(professor);
//
//        long s = System.currentTimeMillis();
//        List<User> users = this.userRepository.findAll();
//        System.err.println(System.currentTimeMillis()-s);
//        users.forEach(u-> System.err.println(u.getClass().getName()));
    }


}
