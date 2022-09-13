package com.bitcamp221.didabara.testpdf;

import com.groupdocs.conversion.Converter;
import com.groupdocs.conversion.filetypes.FileType;
import com.groupdocs.conversion.options.convert.ConvertOptions;
import com.groupdocs.conversion.options.convert.PdfConvertOptions;
import com.groupdocs.conversion.options.load.WordProcessingLoadOptions;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfWriter;
import kr.dogfoot.hwplib.object.HWPFile;
import kr.dogfoot.hwplib.reader.HWPReader;
import kr.dogfoot.hwplib.tool.textextractor.TextExtractMethod;
import kr.dogfoot.hwplib.tool.textextractor.TextExtractor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {

  private final S3UploadTest s3UploadTest;

  @GetMapping
  public ResponseEntity<?> test() throws Exception {
    String filename = "C:\\projectbit\\didabara\\didabaraback\\src\\main\\resources\\static\\imgs\\de2346d1-e0c3-4582-8ab7-c575bf28cdeb.hwp";
    HWPFile hwpFile = HWPReader.fromFile(filename);

    String str = TextExtractor.extract(hwpFile, TextExtractMethod.AppendControlTextAfterParagraphText);

    System.out.println("str = " + str);

//        String str2 = new String(str.getBytes(), StandardCharsets.UTF_8);

    Document document = new Document();
    File file = new File("C:\\Users\\nj\\Downloads\\ggg.pdf");

    try {
      // 2) Writer와 Document 사이의 연관을 맺어줍니다.
      PdfWriter.getInstance(document, new FileOutputStream(file));


      Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);

      // 3)  문서를 오픈합니다.
      document.open();

      FontSelector fs = new FontSelector();

      String str3 = "안녕하세요";

      byte[] utf8 = str3.getBytes("UTF-8");


      String str2 = new String(utf8);

      Phrase process = fs.process(str3);
//            fs.addFont(fontName);

      // 7) 문서에 2개의 paragraph를 각기 다른 본트로 첨부해 보겠습니다.
      document.add(new Paragraph(process.toString()));
      document.add(new Paragraph("aaaaaadd"));


    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      document.add(new Chunk(""));
      document.close();
    }

    String chrome = "C:/Program Files/Google/Chrome/Application/chrome.exe";
    try {
      new ProcessBuilder(chrome, file.getAbsolutePath()).start();
    } catch (IOException e) {
      e.printStackTrace();
    }


    return ResponseEntity.ok().body(file);
  }

  @PostMapping("/test")
  public ResponseEntity test2(MultipartFile file) throws IOException {
    // C:\Users\nj\Downloads\Conholdate.Total_For_Java\GroupDocs.Total_for_Java\GroupDocs.Conversion_21.7-Java\GroupDocs.Conversion_21.7-Java\lib\groupdocs-conversion-21.7.jar
    WordProcessingLoadOptions loadOptions = new WordProcessingLoadOptions();

    File uploadFile = convert(file).orElseThrow(() -> new IllegalArgumentException("file 전달에 실패했습니다."));

    System.out.println("uploadFile.toString() = " + uploadFile.toString());
    Converter converter = new Converter(uploadFile.toString(), loadOptions);


    PdfConvertOptions options = new PdfConvertOptions();

    String code = UUID.randomUUID().toString().substring(0, 6);
    String pathAndPdf = "C:\\Users\\nj\\Downloads\\" + code + ".pdf";

    converter.convert(pathAndPdf, options);

    File pdfFile = new File(pathAndPdf);


    Converter converter1 = new Converter(pathAndPdf.toString());
    ConvertOptions convertOptions = new FileType().fromExtension("jpg").getConvertOptions();
    String pathAndJpg = "C:\\Users\\nj\\Downloads\\" + code + ".jpg";

    converter.convert(pathAndJpg, convertOptions);

    File jpgFile = new File(pathAndJpg);

    String s3UrlJpg = s3UploadTest.upload(jpgFile, "myfile");
    String s3UrlPdf = s3UploadTest.upload(pdfFile, "myfile");

    return ResponseEntity.ok().body(s3UrlPdf + ", " + s3UrlJpg);
  }

  private Optional<File> convert(MultipartFile file) throws IOException {
    File convertFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());
    System.out.println("convertFile = " + convertFile);
    System.out.println("convertFile.getName() = " + convertFile.getName()); //download.jpg
    if (convertFile.createNewFile()) {
      try (FileOutputStream fos = new FileOutputStream(convertFile)) {
        fos.write(file.getBytes());
      }
      return Optional.of(convertFile);
    }
    return Optional.empty();
  }

}