package com.german.studentschedule.services;


import com.german.studentschedule.domain.*;
import com.german.studentschedule.dto.IncomingLessonDto;
import com.german.studentschedule.exceptions.AlreadyExistsException;
import com.german.studentschedule.exceptions.NotFoundException;
import com.german.studentschedule.exceptions.WrongValueException;
import com.german.studentschedule.repository.LessonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackFor = Exception.class)
public class LessonService {

    private final LessonRepository repository;
    private final SubjectService subjectService;
    private final AudienceService audienceService;
    private final GroupService groupService;
    private final UserService userService;

    public LessonService(LessonRepository repository,
                         SubjectService subjectService,
                         AudienceService audienceService,
                         GroupService groupService,
                         UserService userService) {
        this.repository = repository;
        this.subjectService = subjectService;
        this.audienceService = audienceService;
        this.groupService = groupService;
        this.userService = userService;
    }

    public List<Lesson> readAllWithAllNests() {
        return this.repository.findAllWithAllNests();
    }

    public Lesson readByIdWithAllNests(Long id) throws NotFoundException {
        Optional<Lesson> optionalLesson = this.repository.findByIdWithAllNests(id);
        if(optionalLesson.isPresent()) {
            return optionalLesson.get();
        }
        throw new NotFoundException(String.format("There is no lesson with id = %d", id));
    }


    public List<Lesson> readByDateWithAllNests(String dateString) throws WrongValueException, NotFoundException {
        LocalDate date = this.parseDate(dateString);
        return this.repository.findByDateWithAllNests(date);
    }

    public List<Lesson> readByDateForPrincipalWithAllNests(User principal, String dateString) throws WrongValueException, NotFoundException {
        LocalDate date = this.parseDate(dateString);
        Long groupId = this.userService.readGroupIdByUser(principal);
        return this.repository.findByDateForPrincipalWithAllNests(date, groupId);
    }

    public Lesson create(IncomingLessonDto dto) throws WrongValueException, NotFoundException {

        Lesson lesson = new Lesson();

        LocalDate date = this.parseDate(dto.getDate());
        lesson.setDate(date);

        Subject subject;
        Audience audience;
        Group group;
        try {
            if(dto.getSubject()==null) {
                throw new WrongValueException("Subject cannot be null");
            }
            subject = this.subjectService.readByIdWithAllNests(dto.getSubject());

            if(dto.getAudience()==null) {
                throw new WrongValueException("Audience cannot be null");
            }
            audience = this.audienceService.readByIdWithAllNests(dto.getAudience());

            if(dto.getGroup()==null) {
                throw new WrongValueException("Group cannot be null");
            }
            group = this.groupService.readByIdWithAllNests(dto.getGroup());
        } catch (NotFoundException e) {
            throw new WrongValueException(e.getMessage());
        }

        lesson.setSubject(subject);
        lesson.setAudience(audience);
        lesson.setGroup(group);

        return this.repository.save(lesson);
    }

    public Lesson update(Long id, IncomingLessonDto dto) throws NotFoundException, WrongValueException, AlreadyExistsException {

        Lesson lesson = this.readByIdWithAllNests(id);
        LocalDate date = null;
        if(dto.getDate()!=null) {
            date = this.parseDate(dto.getDate());
            lesson.setDate(date);
        }


        try {
            if(dto.getSubject()!=null) {
                Subject subject = this.subjectService.readByIdWithAllNests(dto.getSubject());
                lesson.setSubject(subject);
            }

            if(dto.getAudience()!=null) {
                Audience audience = this.audienceService.readByIdWithAllNests(dto.getAudience());
                lesson.setAudience(audience);
            }

            if(dto.getGroup()!=null) {
                Group group = this.groupService.readByIdWithAllNests(dto.getGroup());
                lesson.setGroup(group);
            }
        } catch (NotFoundException e) {
            throw new WrongValueException(e.getMessage());
        }
        return this.repository.save(lesson);
    }


    public void delete(Long id) {
        this.repository.deleteByIdCustom(id);
    }


    private LocalDate parseDate(String dateString) throws WrongValueException {
        if(dateString==null) {
            throw new WrongValueException("Date cannot be null");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date;
        try {
            date = LocalDate.parse(dateString, formatter);
        } catch (Exception e) {
            throw new WrongValueException("Date must be valid date of format yyyy-MM-dd");
        }
        return date;
    }
}
