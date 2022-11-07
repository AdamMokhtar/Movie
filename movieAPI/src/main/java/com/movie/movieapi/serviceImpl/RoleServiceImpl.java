package com.movie.movieapi.serviceImpl;

import com.movie.movieapi.dto.RoleDto;
import com.movie.movieapi.entity.Role;
import com.movie.movieapi.mapper.RoleMapper;
import com.movie.movieapi.repository.RoleRepository;
import com.movie.movieapi.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.xml.validation.Validator;
import java.util.List;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleMapper roleMapper;
//    @Autowired
//    private Validator validator;

    @Override
    public RoleDto GetRole(String roleName)
    {
        Role role =  roleRepository.findByRoleNameIgnoreCase(roleName);
        if(role != null)
            return roleMapper.toDto(role);
        throw new NotFoundException("Role was not found");
    }
}
