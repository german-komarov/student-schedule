package com.german.studentschedule.config;

import com.german.studentschedule.domain.*;
import com.german.studentschedule.repository.*;
import com.german.studentschedule.util.constants.RoleName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class EventSourcing {

    private final AuditoryRepository auditoryRepository;
    private final GroupRepository groupRepository;
    private final LessonRepository lessonRepository;
    private final RoleRepository roleRepository;
    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;
    private final CorpusRepository corpusRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    @Autowired
    public EventSourcing(AuditoryRepository auditoryRepository,
                         GroupRepository groupRepository,
                         LessonRepository lessonRepository,
                         RoleRepository roleRepository,
                         SubjectRepository subjectRepository,
                         UserRepository userRepository,
                         CorpusRepository corpusRepository,
                         BCryptPasswordEncoder passwordEncoder) {
        this.auditoryRepository = auditoryRepository;
        this.groupRepository = groupRepository;
        this.lessonRepository = lessonRepository;
        this.roleRepository = roleRepository;
        this.subjectRepository = subjectRepository;
        this.userRepository = userRepository;
        this.corpusRepository = corpusRepository;
        this.passwordEncoder = passwordEncoder;
    }



    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        Map<RoleName, Role> rolesByNames = new HashMap<>();

        // Check if database contains anything.
        if(roleRepository.findAll().isEmpty()) {
            for (RoleName rn : RoleName.values()) {
                Role role = this.roleRepository.saveAndFlush(new Role(rn));
                rolesByNames.put(rn, role);
            }


            Corpus corpus = new Corpus("eastern 2");
            corpus = this.corpusRepository.saveAndFlush(corpus);


            Auditory auditory = new Auditory(corpus, 204);
            auditory = this.auditoryRepository.saveAndFlush(auditory);

            Subject subject = new Subject("Math");
            subject = this.subjectRepository.saveAndFlush(subject);

            User student = new User();
            User admin = new User();

            student.setEmail("student1@test.com");
            student.setPassword(this.passwordEncoder.encode("password"));
            student.setEnabled(true);
            student.setRole(rolesByNames.get(RoleName.ROLE_STUDENT));
            student = this.userRepository.saveAndFlush(student);

            admin.setEmail("admin1@test.com");
            admin.setPassword(this.passwordEncoder.encode("password"));
            admin.setEnabled(true);
            admin.setRole(rolesByNames.get(RoleName.ROLE_ADMIN));
            admin = this.userRepository.saveAndFlush(admin);


            Group group = new Group();
            group.setName("CS");
            group.setStudents(Collections.singleton(student));
            group = this.groupRepository.saveAndFlush(group);

            Lesson lesson = new Lesson();
            lesson.setAuditory(auditory);
            lesson.setDate(LocalDate.now().plusDays(1));
            lesson.setSubject(subject);
            lesson.setGroup(group);
            lesson = this.lessonRepository.saveAndFlush(lesson);

        }

    }


}
