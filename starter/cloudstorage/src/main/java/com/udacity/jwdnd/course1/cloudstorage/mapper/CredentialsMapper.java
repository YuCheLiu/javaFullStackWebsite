package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Mapper
@Repository
public interface CredentialsMapper {

    @Insert("INSERT INTO CREDENTIALS (credentialid, url, username, key, password, userid) VALUES (null, #{credentials.url}, #{credentials.username}, #{credentials.key}, #{credentials.password}, #{credentials.userid})")
    int insert(Credentials credentials,String username);

    @Select("SELECT * FROM CREDENTIALS WHERE userid= #{userid}")
    ArrayList<Credentials> queryCredentialList(String userid);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid= #{credentialid}")
    boolean deleteCredential(String credentialid);

    @Update("UPDATE CREDENTIALS SET url=#{credential.url}, username=#{credential.username}, key=#{credential.key}, password=#{credential.password}  WHERE credentialid= #{credential.credentialid}")
    int updateCredential(Credentials credential, Integer credentialid);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid= #{credentialid}")
    Credentials queryaCredentialKey(Integer credentialid);
}
