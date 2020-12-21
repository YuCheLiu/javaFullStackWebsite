package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UsersMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {
    private UsersMapper usersMapper;
    private HashService hashService;

    public UserService(UsersMapper usersMapper, HashService hashService){
        this.usersMapper = usersMapper;
        this.hashService = hashService;
    }

    public boolean isUsernameAvailable(String username){
        return usersMapper.getUsers(username)==null;
    }

    public int createUser(Users users){
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(users.getPassword(),encodedSalt);
        return usersMapper.insert(new Users(null, users.getUsername(), encodedSalt, hashedPassword, users.getFirstName(), users.getLastName()));
    }
}
