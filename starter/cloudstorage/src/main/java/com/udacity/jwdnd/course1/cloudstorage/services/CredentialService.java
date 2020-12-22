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
        ArrayList<Credentials> list = credentialsMapper.queryCredentialList(usersMapper.getUsers(username).getUserid().toString());
        for (Credentials i: list) {
            String plainPassword = encryptionService.decryptValue(i.getPassword(),i.getKey());
            i.setPassword(plainPassword);
        }
        return list;
    }

    public void addCredential(CredentialForm credentialForm, String username){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);

        Credentials credentials = new Credentials(null, credentialForm.getUrl(), credentialForm.getUsername(), encodedKey, encryptionService.encryptValue(credentialForm.getPassword(),encodedKey),usersMapper.getUsers(username).getUserid());

        credentialsMapper.insert(credentials,username);
    }

    public void deleteCredential(Integer credentialid){
        credentialsMapper.deleteCredential(credentialid.toString());
    }

    public void updateCredential(CredentialForm credentials, String username){

        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);

        Credentials credential = new Credentials(credentials.getCredentialId(),credentials.getUrl(),credentials.getUsername(),encodedKey,encryptionService.encryptValue(credentials.getPassword(),encodedKey), usersMapper.getUserId(username));
        credentialsMapper.updateCredential(credential,credentials.getCredentialId());
    }
}
