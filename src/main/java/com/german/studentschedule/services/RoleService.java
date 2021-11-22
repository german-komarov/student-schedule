package com.german.studentschedule.services;

import com.german.studentschedule.domain.Role;
import com.german.studentschedule.repository.RoleRepository;
import com.german.studentschedule.util.constants.RoleName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class RoleService {

    private final RoleRepository repository;

    @Autowired
    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    Role readByName(RoleName name) {
        return this.repository.findByName(name);
    }
}
