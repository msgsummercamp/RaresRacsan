package com.example.pdf;

import com.itextpdf.text.Document;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.File;
import java.util.Scanner;

@Component
public class PdfGenUI {

    private final PdfGenService pdfGenService;
    private final Scanner scanner;

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

        Document document = pdfGenService.createNewDocument();
        try {
            pdfGenService.openDocument(fullPath, document);
            System.out.println("Document opened successfully.");

            while (true) {
                System.out.println("Choose an option: 1 - Add text, 2 - Add Image, 3 - Close");
                String option = scanner.nextLine();
                if ("1".equals(option)) {
                    System.out.print("Enter the text to add to the PDF: ");
                    String text = scanner.nextLine();
                    pdfGenService.addText(text, document);
                    System.out.println("Text added to PDF: " + text);
                } else if ("2".equals(option)) {
                    System.out.print("Enter the path of the image to add to the PDF (e.g., myImage.png in resources): ");
                    String imagePath = scanner.nextLine();
                    try {
                        pdfGenService.addImage(imagePath, document);
                        System.out.println("Image added to PDF: " + imagePath);
                    } catch (Exception e) {
                        System.err.println("Error adding image: " + e.getMessage());
                    }
                } else if ("3".equals(option)) {
                    break;
                } else {
                    System.out.println("Invalid option. Please try again.");
                }
            }
        } catch (Exception e) {
            System.err.println("Error generating PDF: " + e.getMessage());
        } finally {
            pdfGenService.closeDocument(document);
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
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println("Error modifying existing PDF: " + e.getMessage());
        }
    }
}