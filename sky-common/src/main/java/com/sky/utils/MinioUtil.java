package com.sky.utils;



import java.io.ByteArrayInputStream;


import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@AllArgsConstructor
@Slf4j
public class MinioUtil {

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;

    /**
     * 文件上传
     *
     * @param bytes
     * @param objectName
     * @return
     */
    public String upload(byte[] bytes, String objectName) {
        try {
            // 1. 创建 MinioClient
            MinioClient minioClient = MinioClient.builder()
                    .endpoint("https://" + endpoint) // 注意这里要带协议
                    .credentials(accessKeyId, accessKeySecret)
                    .build();

            // List<Bucket> listBuckets = minioClient.listBuckets();
            // 2. 检查 bucket 是否存在
            boolean found = minioClient.bucketExists(
                    BucketExistsArgs.builder().bucket(bucketName).build()
            );
            if (!found) {
                minioClient.makeBucket(
                        MakeBucketArgs.builder().bucket(bucketName).build()
                );
                log.info("Bucket 不存在，已创建: {}", bucketName);
            }

            // 3. 上传文件（核心）
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(bais, bytes.length, -1)
                            .contentType("application/octet-stream") // 可根据文件类型动态设置
                            .build()
            );

            // 4. 拼接访问路径
            String url = "https://" + endpoint + "/" + bucketName + "/" + objectName;

            log.info("文件上传成功: {}", url);

            return url;

        } catch (Exception e) {
            log.error("MinIO上传失败", e);
            throw new RuntimeException("文件上传失败");
        }
    }
}
