package com.bitcamp221.didabara.dto;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.bitcamp221.didabara.mapper.UserInfoMapper;
import com.bitcamp221.didabara.model.UserInfoEntity;
import com.bitcamp221.didabara.presistence.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
@PropertySource(value="application.properties")
public class S3Upload {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3Client s3Client;



    public String getS3(String bucket, String fileName) {

        return s3Client.getUrl(bucket, fileName).toString();
    }

    public String upload(MultipartFile file, String dirName, String id) throws IOException {
        File uploadFile = convert(file).orElseThrow(() -> new IllegalArgumentException("file 전달에 실패했습니다."));
        String extension = uploadFile.getName().substring(uploadFile.getName().lastIndexOf("."));
        System.out.println("extension = " + extension);
        File news= new File(System.getProperty("user.dir") + "/" + UUID.randomUUID() + extension);
        uploadFile.renameTo(news);
        return upload(news, dirName,id);
    }

    public String upload(File uploadFile, String dirName, String id) {
        String fileName = dirName + "/" + uploadFile.getName();
        String uploadImageURI = putS3(uploadFile, fileName);
        String dBPathName = uploadImageURI.substring(0, 56);
        String dbFilename = uploadImageURI.substring(uploadImageURI.lastIndexOf("/") + 1);

        Optional<UserInfoEntity> byId = userInfoRepository.findById(Long.valueOf(id));
        byId.get().setProfileImageUrl(dBPathName);
        byId.get().setFilename(dbFilename);
        userInfoRepository.save(byId.get());

       /* UserInfoEntity byIdInUserInfo = userInfoMapper.findByIdInUserInfo(id);
        byIdInUserInfo.setProfileImageUrl(dBPathName);
        byIdInUserInfo.setFilename(dbFilename);
        userInfoMapper.updateImage(byIdInUserInfo);*/
        removeNewFile(uploadFile);
        return uploadImageURI;
    }

    private String putS3(File uploadFile, String fileName) {
        s3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return s3Client.getUrl(bucket, fileName).toString();
    }

    public int deleteFile(String fileName, String id) {
        Optional<UserInfoEntity> byId = userInfoRepository.findById(Long.valueOf(id));
        String filename = byId.get().getFilename();
        System.out.println("filename = " + filename);
        if (fileName.equals(filename)){
            userInfoMapper.updateEmpty(id);
        }
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, "myfile/"+fileName);
        s3Client.deleteObject(deleteObjectRequest);
        return 1;
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일 삭제 완료");
        }
        else {
            log.info("파일 삭제 실패");
        }
    }

    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());
        System.out.println("convertFile = " + convertFile);
        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)){
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }
}