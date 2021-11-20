package com.german.studentschedule.services;


import com.german.studentschedule.repository.AuditoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class AuditoryService {

    private final AuditoryRepository repository;


    @Autowired
    public AuditoryService(AuditoryRepository repository) {
        this.repository = repository;
    }





}
