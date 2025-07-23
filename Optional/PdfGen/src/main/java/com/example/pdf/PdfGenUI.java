package com.example.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class PdfGenUI {

    private final PdfGenService pdfGenService;
    private final Scanner scanner;
    private static final Logger logger = LoggerFactory.getLogger(PdfGenUI.class);

    @Autowired
    public PdfGenUI(PdfGenService pdfGenService) {
        this.pdfGenService = pdfGenService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Choose an option: 1 - Create new PDF, 2 - Modify existing PDF");
        String choice = scanner.nextLine();

        if ("1".equals(choice)) {
            createNewPdfFlow();
        } else if ("2".equals(choice)) {
            modifyExistingPdfFlow();
        } else {
            System.out.println("Invalid option. Exiting.");
        }
    }

    private void createNewPdfFlow() {
        System.out.print("Enter the name of the PDF file to generate: ");
        String fileName = scanner.nextLine();

        if (!fileName.endsWith(".pdf")) {
            fileName += ".pdf";
        }

        String fullPath = pdfGenService.getOutputDir() + File.separator + fileName;
        System.out.println("PDF will be generated as: " + fullPath);

        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(fullPath));
            document.open();
            System.out.println("Document opened successfully.");

            while (true) {
                System.out.println("Choose an option: 1 - Add text, 2 - Add Image, 3 - Close");
                String option = scanner.nextLine();
                switch (option) {
                    case "1":
                        System.out.print("Enter the text to add to the PDF: ");
                        String text = scanner.nextLine();
                        document.add(new Paragraph(text));
                        System.out.println("Text added to PDF: " + text);
                        break;
                    case "2":
                        System.out.print("Enter the path of the image to add to the PDF (e.g., myImage.png in resources): ");
                        String imagePath = scanner.nextLine();
                        try {
                            pdfGenService.addImage(imagePath, document);
                            System.out.println("Image added to PDF: " + imagePath);
                        } catch (Exception e) {
                            logger.error("Error adding image: " + e.getMessage());
                        }
                        break;
                    case "3":
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            }
        } catch (Exception e) {
            logger.error("Error generating PDF: {}", e.getMessage(), e);
        } finally {
            if (document.isOpen()) {
                document.close();
            }
            System.out.println("Document closed successfully.");
        }
    }

    private void modifyExistingPdfFlow() {
        System.out.print("Enter the name of the existing PDF in resources (e.g., existing.pdf): ");
        String resourcePdf = scanner.nextLine();

        System.out.print("Enter the text to add: ");
        String textToAdd = scanner.nextLine();

        try {
            pdfGenService.addTextToExistingPdf(resourcePdf, textToAdd);
            System.out.println("Text added to end of existing PDF. Overwritten: " + resourcePdf);
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {
            logger.error("Error modifying existing PDF: " + e.getMessage());
        }
    }
}