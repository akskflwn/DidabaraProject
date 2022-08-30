package com.bitcamp221.didabara.controller;

import com.bitcamp221.didabara.dto.S3Upload;
import com.bitcamp221.didabara.model.UserInfoEntity;
import com.bitcamp221.didabara.presistence.UserInfoRepository;
import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.URL;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/upload")
public class FileUploadController {

    private final S3Upload s3Upload;

    private final UserInfoRepository userInfoRepository;

    @PostMapping
    public ResponseEntity<String> uploadFile(@RequestParam("images") MultipartFile multipartFile,
                                             @AuthenticationPrincipal String id
    ) throws IOException {

        return ResponseEntity.ok().body(s3Upload.upload(multipartFile, "myfile", id));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Integer> deleteFile(String images,@AuthenticationPrincipal String id) throws IOException {

        return ResponseEntity.ok().body(s3Upload.deleteFile(images,id));
    }

    @GetMapping
    public ResponseEntity<?> getMyFile(@AuthenticationPrincipal String id) throws Exception {
        System.out.println("id = " + id);

        Optional<UserInfoEntity> byId = userInfoRepository.findById(Long.valueOf(id));

        String filename = byId.get().getFilename();
        String profileImageUrl = byId.get().getProfileImageUrl();
        String wordFileName = profileImageUrl + filename;
        System.out.println("wordFileName = " + wordFileName);

        String pdfCode = "toPDF" + UUID.randomUUID().toString().substring(0, 6);

        String toPdfFileName = profileImageUrl + pdfCode + ".pdf";
        System.out.println("toPdfFileName = " + toPdfFileName);

        File f = new File("C:\\Users\\nj\\Downloads\\docxtest");
        String s3 = s3Upload.getS3("didabara/myfile", "b11a0412-4027-469e-a02f-98f646e36c6e.docx");
        FileUtils.copyURLToFile(new URL(s3),f);

        // 1. docx 가져오기
        String docxFileName = "C:\\Users\\nj\\Downloads\\test.docx";
        // 2. 로컬에 저장
        
        // 3. 로컬에 저장한 docx pdf로 변환
        
        // 4. pdf로 변환한 값 s3에 저장
        String pdfFileName = "C:\\Users\\nj\\Downloads\\aaa.pdf";

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
