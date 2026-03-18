package com.sky.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sky.properties.MinioProperties;
import com.sky.utils.MinioUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 配置类，用于创建 OssUtil 对象
 */
@Configuration
@Slf4j
public class OssConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public MinioUtil minioUtil(MinioProperties minioProperties) {
        log.info("开始创建Minio文件上传工具类对象：{}", minioProperties);
        return new MinioUtil(
            minioProperties.getEndpoint(), 
            minioProperties.getAccessKeyId(), 
            minioProperties.getAccessKeySecret(), 
            minioProperties.getBucketName()
        );
    }
}
