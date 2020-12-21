package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UsersMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;

@Service
public class CredentialService {
    private UsersMapper usersMapper;
    private CredentialsMapper credentialsMapper;
    private EncryptionService encryptionService;

    public CredentialService(CredentialsMapper credentialsMapper, UsersMapper usersMapper, EncryptionService encryptionService){
        this.usersMapper = usersMapper;
        this.credentialsMapper = credentialsMapper;
        this.encryptionService = encryptionService;
    }

    public ArrayList<Credentials> getCredentialList(String username){
        return credentialsMapper.queryCredentialList(usersMapper.getUsers(username).getUserid().toString());
    }

    public void addCredential(CredentialForm credentialForm, String username){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);

        System.out.print("key generation: "+encodedKey);
        Credentials credentials = new Credentials(null, "a", encodedKey, "aaa",usersMapper.getUsers(username).getUserid());
        System.out.print("credential generation");
        //this one doesn't work
        //credentialsMapper.insert(credentials, "123a");
        //this one works
        credentialsMapper.insert(credentials,username,"123");
    }

    public void deleteCredential(Integer credentialid){
        credentialsMapper.deleteCredential(credentialid.toString());
    }

    public void updateCredential(Credentials credentials, Integer credentialid){
        credentialsMapper.updateCredential(credentials, credentialid);
    }
}
