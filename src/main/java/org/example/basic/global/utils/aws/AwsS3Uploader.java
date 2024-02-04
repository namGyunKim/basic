package org.example.basic.global.utils.aws;

import jakarta.annotation.PostConstruct;
import org.example.basic.global.utils.aws.constant.FileDirectory;
import org.example.basic.global.utils.aws.constant.FileType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class AwsS3Uploader {

    @Value(value = "${app.aws.s3.accessKey}")
    private String accessKey;
    @Value(value = "${app.aws.s3.secretKey}")
    private String secretKey;
    @Value(value = "${app.aws.s3.bucketName}")
    private String bucketName;

    private S3Client s3Client;

    @PostConstruct
    public void init() {
        Region region = Region.AP_NORTHEAST_2;
        AwsCredentials awsCredentials = AwsBasicCredentials.create(accessKey, secretKey);

        this.s3Client = S3Client.builder()
                .region(region)
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .build();
    }


    public String upload(FileType fileType, MultipartFile multipartFile, FileDirectory fileDirectory) throws IOException {

        final String key = keyNameMaker(fileType.getValue(), multipartFile.getOriginalFilename(), fileDirectory);

        RequestBody requestBody = RequestBody.fromInputStream(
                new ByteArrayInputStream(multipartFile.getBytes()),
                multipartFile.getSize()
        );

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(multipartFile.getContentType())
                .contentDisposition("inline")
                .acl(ObjectCannedACL.PUBLIC_READ)
                .build();

        s3Client.putObject(request, requestBody);

//        storedFileName 을 반환한다
        return URLDecoder.decode(s3Client.utilities().getUrl(builder -> builder.bucket(bucketName).key(key).build()).toString(), StandardCharsets.UTF_8);
    }

    public void delete(FileType fileType, String storedFileName, FileDirectory fileDirectory) {
        final String bucketName = fileType.getValue();
        String fileDate = storedFileName.substring(storedFileName.lastIndexOf("/") - 10, storedFileName.lastIndexOf("/"));
        String objectKey = bucketName + "/" + fileDirectory.getValue() + "/" + fileDate + "/" + storedFileName.substring(storedFileName.lastIndexOf("/") + 1);
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();
        s3Client.deleteObject(deleteObjectRequest);
    }

    public String keyNameMaker(String bucketName, String originalFileName, FileDirectory fileDirectory) {
        String uuid = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();
        String currentDate = String.format("%d-%02d-%02d/", now.getYear(), now.getMonthValue(), now.getDayOfMonth());
        return bucketName + '/' + fileDirectory.getValue() + '/' + currentDate + uuid.substring(0, 9) + '-' + originalFileName;
    }
}
