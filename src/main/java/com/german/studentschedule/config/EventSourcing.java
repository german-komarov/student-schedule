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

    private final AudienceRepository audienceRepository;
    private final GroupRepository groupRepository;
    private final LessonRepository lessonRepository;
    private final RoleRepository roleRepository;
    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;
    private final CorpusRepository corpusRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    @Autowired
    public EventSourcing(AudienceRepository audienceRepository,
                         GroupRepository groupRepository,
                         LessonRepository lessonRepository,
                         RoleRepository roleRepository,
                         SubjectRepository subjectRepository,
                         UserRepository userRepository,
                         CorpusRepository corpusRepository,
                         BCryptPasswordEncoder passwordEncoder) {
        this.audienceRepository = audienceRepository;
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


            Audience audience = new Audience(corpus, 204);
            audience = this.audienceRepository.saveAndFlush(audience);

            Subject subject = new Subject("Math");
            subject = this.subjectRepository.saveAndFlush(subject);


            Group group = new Group();
            group.setName("CS");
            group.setSubjects(Collections.singleton(subject));
            group = this.groupRepository.saveAndFlush(group);


            User student = new User();
            User admin = new User();
            User superAdmin = new User();

            student.setEmail("student1@test.com");
            student.setPassword(this.passwordEncoder.encode("password"));
            student.setEnabled(true);
            student.setRoles(Collections.singleton(rolesByNames.get(RoleName.ROLE_STUDENT)));
            student.setGroup(group);
            student = this.userRepository.saveAndFlush(student);

            admin.setEmail("admin1@test.com");
            admin.setPassword(this.passwordEncoder.encode("password"));
            admin.setEnabled(true);
            admin.setRoles(Collections.singleton(rolesByNames.get(RoleName.ROLE_ADMIN)));
            admin = this.userRepository.saveAndFlush(admin);

            superAdmin.setEmail("superadmin1@test.com");
            superAdmin.setPassword(this.passwordEncoder.encode("password"));
            superAdmin.setEnabled(true);
            superAdmin.setRoles(Collections.singleton(rolesByNames.get(RoleName.ROLE_SUPER_ADMIN)));
            superAdmin = this.userRepository.saveAndFlush(superAdmin);

            Lesson lesson = new Lesson();
            lesson.setAuditory(audience);
            lesson.setDate(LocalDate.now().plusDays(1));
            lesson.setSubject(subject);
            lesson.setGroup(group);
            lesson = this.lessonRepository.saveAndFlush(lesson);

        }

    }


}
