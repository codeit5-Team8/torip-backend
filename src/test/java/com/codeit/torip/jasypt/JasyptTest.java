package com.codeit.torip.jasypt;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@ActiveProfiles("test")
public class JasyptTest {

    @Value("${jasypt.encryptor.key:TEST_KEY}")
    String key;


    @Test
    void jasypt() {
        System.out.println("Jasypt Secret Key : " + key);
        String secret = "TEST";

        String encodingKey = jasyptEncoding(secret);
        String decodingKey = jasyptDecoding(encodingKey);

        System.out.printf("%s -> %s\n", secret, encodingKey);
        System.out.printf("%s -> %s\n", encodingKey, decodingKey);

        Assertions.assertEquals(secret, decodingKey, "The decoded key does not match the original secret");
    }

    public String jasyptEncoding(String value) {
        StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
        pbeEnc.setAlgorithm("PBEWithMD5AndDES");
        pbeEnc.setPassword(key);
        return pbeEnc.encrypt(value);
    }

    public String jasyptDecoding(String encryptedValue) {
        StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
        pbeEnc.setAlgorithm("PBEWithMD5AndDES");
        pbeEnc.setPassword(key);
        return pbeEnc.decrypt(encryptedValue);
    }

}