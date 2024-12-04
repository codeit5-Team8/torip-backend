package com.codeit.torip.common.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JasyptConfig {

    @Value("${jasypt.encryptor.key}")
    String key;

    @Bean(name = "jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        var encryptor = new PooledPBEStringEncryptor();
        var config = new SimpleStringPBEConfig();
        config.setPassword(key);                        // 암호화에 사용되는 비밀 키
        config.setAlgorithm("PBEWithMD5AndDES");        // 사용할 암호화 알고리즘
        config.setKeyObtentionIterations("1000");       // 키 생성을 위해 수행되는 반복 횟수 (암호화의 강도를 높이는 데 사용)
        config.setPoolSize("1");                        // 사용할 암호화 엔진의 수
        config.setProviderName("SunJCE");               // 암호화 제공자
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");    // 솔트 생성기
        config.setStringOutputType("base64");           // 암호화된 문자열의 출력 형식
        encryptor.setConfig(config);
        return encryptor;
    }
}