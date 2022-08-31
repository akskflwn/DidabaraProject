package com.bitcamp221.didabara.controller;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3URI;
import com.amazonaws.services.s3.model.S3Object;
import com.bitcamp221.didabara.dto.S3Upload;
import com.bitcamp221.didabara.presistence.UserInfoRepository;
import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;
import kr.dogfoot.hwplib.object.HWPFile;
import kr.dogfoot.hwplib.reader.HWPReader;
import kr.dogfoot.hwplib.tool.textextractor.TextExtractMethod;
import kr.dogfoot.hwplib.tool.textextractor.TextExtractor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/upload")
public class FileUploadController {

    private final S3Upload s3Upload;
    private final AmazonS3Client s3Client;

    private final UserInfoRepository userInfoRepository;

    @PostMapping
    public ResponseEntity<String> uploadFile(@RequestParam("images") MultipartFile files,
                                             @AuthenticationPrincipal String id
    ) throws Exception {

        System.out.println("files.toString() = " + files.toString());

        // db에 저장할 파일 이름 생성
        String code = UUID.randomUUID().toString().substring(0, 6);

        // 매개변수로 들어온 파일의 이름 가져오기
        String sourceFileName = files.getOriginalFilename();
        // 파일의 확장자 가져오기
        String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase();

        File destinationFile;
        File pdfFile;
        HWPFile hwpFile;
        String destinationFileName;

        String fileUrl = "C:\\projectbit\\didabara\\didabaraback\\src\\main\\resources\\static\\imgs\\";


        destinationFileName = code + "." + sourceFileNameExtension;
        destinationFile = new File(fileUrl + destinationFileName);
        pdfFile = new File(fileUrl + code + ".pdf");
        hwpFile = HWPReader.fromFile(fileUrl + "ddd" + ".hwp");
        String hwpTest = TextExtractor.extract(hwpFile, TextExtractMethod.InsertControlTextBetweenParagraphText);

        String hwpfileName = "C:\\projectbit\\didabara\\didabaraback\\src\\main\\resources\\static\\imgs\\testHwp.hwp";

        File file = new File(hwpfileName);

        FileOutputStream fos = new FileOutputStream(file);

        fos.write(Integer.parseInt(hwpTest));

        destinationFile.getParentFile().mkdirs();


        files.transferTo(destinationFile);

        Files.copy(destinationFile.toPath(), pdfFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        //
        IConverter converter = LocalConverter.builder().build();

        converter.convert(destinationFile).as(DocumentType.MS_WORD)
                .to(pdfFile).as(DocumentType.PDF)
                .execute();

        converter.shutDown();
        destinationFile.delete();
        //s3Upload.deleteFile(destinationFileName,id);
        //
        // return ResponseEntity.ok().body("ok");
        return ResponseEntity.ok().body(s3Upload.upload(pdfFile, "myfile", id));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Integer> deleteFile(String images,@AuthenticationPrincipal String id) throws IOException {

        return ResponseEntity.ok().body(s3Upload.deleteFile(images,id));
    }

    @GetMapping("/test")
    public ResponseEntity<?> getMyFile2(@AuthenticationPrincipal String id,HttpServletRequest request, HttpServletResponse response) throws Exception {
        s3Upload.download("myfile/d6a2d19c-b792-4c32-9fa7-cab8b9376d41.jpg",
                "d6a2d19c-b792-4c32-9fa7-cab8b9376d41.jpg", request, response);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping
    public ResponseEntity<?> getMyFile(@AuthenticationPrincipal String id) throws Exception {
//        System.out.println("id = " + id);
//
//        Optional<UserInfoEntity> byId = userInfoRepository.findById(Long.valueOf(id));
//
//        String filename = byId.get().getFilename();
//        String profileImageUrl = byId.get().getProfileImageUrl();
//        String wordFileName = profileImageUrl + filename;
//        System.out.println("wordFileName = " + wordFileName);
//
//        String pdfCode = "toPDF" + UUID.randomUUID().toString().substring(0, 6);
//
//        String toPdfFileName = profileImageUrl + pdfCode + ".pdf";
//        System.out.println("toPdfFileName = " + toPdfFileName);


        URI fileToBeDownloaded = new URI("s3://didabara/myfile/b11a0412-4027-469e-a02f-98f646e36c6e.docx");

        AmazonS3URI s3URI = new AmazonS3URI(fileToBeDownloaded);
        System.out.println("s3URI.getKey() = " + s3URI.getKey());
        S3Object s3Object = s3Client.getObject("didabara", s3URI.getKey());
        System.out.println("s3Object = " + s3Object);
        System.out.println("s3Object = " + s3Object.toString());
        // 1. docx 가져오기
        String docxFileName = "C:\\Users\\nj\\Downloads\\abcdefg.docx";
        // 2. 로컬에 저장

        // 3. 로컬에 저장한 docx pdf로 변환

        // 4. pdf로 변환한 값 s3에 저장
        String pdfFileName = "C:\\Users\\nj\\Downloads\\test.pdf";

        try (InputStream docxInputStream = new FileInputStream(s3Object.toString());
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