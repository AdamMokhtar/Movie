package com.movie.movieapi.serviceImpl;

import com.movie.movieapi.dto.MovieDto;
import com.movie.movieapi.dto.UserDto;
import com.movie.movieapi.entity.Movie;
import com.movie.movieapi.entity.SecurityUser;
import com.movie.movieapi.entity.User;
import com.movie.movieapi.exception.MovieNotFound;
import com.movie.movieapi.mapper.UserMapper;
import com.movie.movieapi.repository.UserRepository;
import com.movie.movieapi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.NameAlreadyBoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class UserServiceImpl implements UserService,UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private Validator validator;
    @Lazy
    @Autowired
    private PasswordEncoder encoder;

    /**
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .map(SecurityUser::new)
                .orElseThrow(()-> new UsernameNotFoundException("The user "+username+ " was not found"));
    }

    /**
     *
     * @return
     */
    @Override
    public List<UserDto> getAllUsers() {
        log.info("Entering the getAllUsers");
        List<User> userList = userRepository.findAll();
        return userMapper.toDto(userList);
    }

    /**
     *
     * @param userDto
     * @return
     */
    @Override
    public UserDto postUser(UserDto userDto) throws NameAlreadyBoundException { //need to check this!!
        log.info("Entering the post user method");
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        if(violations.isEmpty()){
            if (!checkUserNameInDB(userDto)) {
                log.info("There is no similar username in the database");
                User user = userMapper.toEntity(userDto);
                //set password using encoder
                String pass = user.getPassword();
                user.setPassword(encoder.encode(pass));
                user = userRepository.saveAndFlush(user);
                return userMapper.toDto(user);
            } else {
                throw new NameAlreadyBoundException("There is a similar user name to "+userDto.getUsername());
            }
        }else
            throw new ConstraintViolationException("Validations were not passed",violations);
    }

    /**
     *
     * @param userDto
     * @return
     */
    @Override
    public boolean checkUserNameInDB(UserDto userDto)
    {
        int count = (int) getAllUsers().stream()
                .filter(mo->mo.getUsername().equalsIgnoreCase(userDto.getName())).count();
        if(count > 0)
            return true;
        return false;
    }

}
