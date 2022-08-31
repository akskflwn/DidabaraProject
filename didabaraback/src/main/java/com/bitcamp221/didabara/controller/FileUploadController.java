package com.bitcamp221.didabara.controller;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3URI;
import com.bitcamp221.didabara.dto.S3Upload;
import com.bitcamp221.didabara.model.UserInfoEntity;
import com.bitcamp221.didabara.presistence.UserInfoRepository;
import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/upload")
public class FileUploadController {

    private final S3Upload s3Upload;

    private final UserInfoRepository userInfoRepository;

    @PostMapping("text")
    private ResponseEntity<?> uploadText(@RequestParam("images") MultipartFile files,
                                         @AuthenticationPrincipal String id) throws IOException {
        return ResponseEntity.ok().body(s3Upload.upload(files, "myfile",id));
    }

    @PostMapping
    public ResponseEntity<String> uploadFile(@RequestParam("images") MultipartFile files,
                                             @AuthenticationPrincipal String id
    ) throws IOException {

        // db에 저장할 파일 이름 생성
        String code = UUID.randomUUID().toString().substring(0, 6);

        // 매개변수로 들어온 파일의 이름 가져오기
        String sourceFileName = files.getOriginalFilename();
        // 파일의 확장자 가져오기
        String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase();

        File destinationFile;
        File pdfFile;
        String destinationFileName;

        String fileUrl = "C:\\projectbit\\didabara\\didabaraback\\src\\main\\resources\\static\\imgs\\";
//        String fileUrl = "https://didabara.s3.ap-northeast-2.amazonaws.com/myfile/";


        destinationFileName = code + "." + sourceFileNameExtension;
        destinationFile = new File(fileUrl + destinationFileName);
        pdfFile = new File(fileUrl + code + ".pdf");


        destinationFile.getParentFile().mkdirs();


        files.transferTo(destinationFile);

        Files.copy(destinationFile.toPath(), pdfFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        //
        IConverter converter = LocalConverter.builder().build();

        converter.convert(destinationFile).as(DocumentType.MS_WORD)
                .to(pdfFile).as(DocumentType.PDF)
                .execute();

        converter.shutDown();
        //s3Upload.deleteFile(destinationFileName,id);
        //
        // return ResponseEntity.ok().body("ok");
        return ResponseEntity.ok().body(s3Upload.upload(pdfFile, "myfile", id));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Integer> deleteFile(String images, @AuthenticationPrincipal String id) throws IOException {

        return ResponseEntity.ok().body(s3Upload.deleteFile(images, id));
    }

    @GetMapping
    public ResponseEntity<?> getMyFile(@AuthenticationPrincipal String id) throws Exception {

        // 1. docx 가져오기
        String docxFileName = "https://s3://didabara-bucket/myfile/test.docx";
        // 2. 로컬에 저장

        // 3. 로컬에 저장한 docx pdf로 변환

        // 4. pdf로 변환한 값 s3에 저장
        String pdfFileName = "https://s3://didabara-bucket/myfile/test.pdf";

        try (InputStream docxInputStream = new FileInputStream(docxFileName);
             OutputStream pdfOutputStream = new FileOutputStream(pdfFileName)) {

            IConverter converter = LocalConverter.builder().build();

            converter.convert(docxInputStream).as(DocumentType.MS_WORD)
                    .to(pdfOutputStream).as(DocumentType.PDF)
                    .execute();

            converter.shutDown();

            return ResponseEntity.ok().body(converter);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().body(null);

    }

}