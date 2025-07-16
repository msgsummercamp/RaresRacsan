package com.example.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

@Service
public class PdfGenService implements CommandLineRunner {
    private String fileName;
    private String text;
    private String imagePath;

    public static void main(String[] args) {
        SpringApplication.run(PdfGenService.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        generateDocument();
    }

    public void generateDocument() {
        // Write in terminal / command line the name of the generated PDF file
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the PDF file to generate: ");
        fileName = scanner.nextLine();
        if (!fileName.endsWith(".pdf")) {
            fileName += ".pdf";
        }
        System.out.println("PDF will be generated as: " + fileName);
        // Logic to create a PDF document
        Document document = new Document();
        try {
            // Open the document with the specified file name
            openDocument(fileName, document);
            while(true) {
                System.out.println("Choose an option: 1 - Add text, 2 - Add Image, 3 - Close");
                String option = scanner.nextLine();
                if ("1".equals(option)) {
                    addText(text, document);
                } else if ("2".equals(option)) {
                    addImage(imagePath, document);
                } else if ("3".equals(option)) {
                    break; // Exit the loop to close the document
                } else {
                    System.out.println("Invalid option. Please try again.");
                }
            }
            // Close the document
            closeDocument(document);
            return;
        } catch (Exception e) {
            System.err.println("Error generating PDF: " + e.getMessage());
        }
    }

    public void openDocument(String fileName, Document document) throws Exception {
        // Initialize the PDF writer and open the document
        PdfWriter.getInstance(document, new FileOutputStream(fileName));
        document.open();
        System.out.println("Document opened successfully.");
    }

    public void closeDocument(Document document) {
        // Close the document
        if (document.isOpen()) {
            document.close();
            System.out.println("Document closed successfully.");
        } else {
            System.out.println("Document is already closed.");
        }
    }

    public void addText(String text, Document document) {
        // Logic to add text to the PDF document
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the text to add to the PDF: ");
        text = scanner.nextLine();

        try {
            // Add text to the document
            document.add(new com.itextpdf.text.Paragraph(text));
            System.out.println("Text added to PDF: " + text);
        } catch (Exception e) {
            System.err.println("Error adding text to PDF: " + e.getMessage());
        }
    }

    public void addImage(String imagePath, Document document) {
        // Logic to add an image to the PDF document
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the path of the image to add to the PDF: ");
        imagePath = scanner.nextLine();

        try {
            Path path = Paths.get(ClassLoader.getSystemResource(imagePath).toURI());
            Image image = Image.getInstance(path.toAbsolutePath().toString());
            document.add(image);
            System.out.println("Image added to PDF: " + imagePath);
        } catch (Exception e) {
            System.err.println("Error adding image to PDF: " + e.getMessage());
        }
    }
}
