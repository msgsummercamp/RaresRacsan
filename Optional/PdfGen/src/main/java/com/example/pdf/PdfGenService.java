package com.example.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class PdfGenService {
    @Value("${pdf.output.dir}")
    private String outputDir;

    public String getOutputDir() {
        return outputDir;
    }

    public Document createNewDocument() {
        return new Document();
    }

    public void openDocument(String fullPath, Document document) throws Exception {
        PdfWriter.getInstance(document, new FileOutputStream(fullPath));
        document.open();
    }

    public void closeDocument(Document document) {
        if (document.isOpen()) {
            document.close();
        }
    }

    public void addText(String text, Document document) throws Exception {
        document.add(new com.itextpdf.text.Paragraph(text));
    }

    public void addImage(String imagePath, Document document) throws Exception {
        Path path = Paths.get(ClassLoader.getSystemResource(imagePath).toURI());
        Image image = Image.getInstance(path.toAbsolutePath().toString());
        document.add(image);
    }

    public void addTextToExistingPdf(String resourcePdfName, String textToAdd) throws Exception {
        Path resourcePath = Paths.get("src/main/resources", resourcePdfName);
        if (!resourcePath.toFile().exists()) {
            throw new IllegalArgumentException("Resource PDF not found: " + resourcePdfName);
        }
        Path tempPath = Paths.get("src/main/resources", "temp_" + resourcePdfName);
        PdfReader reader = null;
        PdfStamper stamper = null;
        try {
            reader = new PdfReader(resourcePath.toAbsolutePath().toString());
            stamper = new PdfStamper(reader, new FileOutputStream(tempPath.toAbsolutePath().toString()));
            int newPage = reader.getNumberOfPages() + 1;
            stamper.insertPage(newPage, reader.getPageSize(1));
            PdfContentByte content = stamper.getOverContent(newPage);
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
            content.beginText();
            content.setFontAndSize(bf, 14);
            content.setTextMatrix(50, 800); // Top of new page
            content.showText(textToAdd);
            content.endText();
        } finally {
            try { if (stamper != null) stamper.close(); } catch (Exception ignored) {}
            try { if (reader != null) reader.close(); } catch (Exception ignored) {}
        }
        System.gc();
        Thread.sleep(200);
        java.nio.file.Files.delete(resourcePath);
        java.nio.file.Files.move(tempPath, resourcePath);
    }
}