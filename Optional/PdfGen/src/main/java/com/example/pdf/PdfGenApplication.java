package com.example.pdf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
public class PdfGenApplication implements CommandLineRunner {

    private final PdfGenUI pdfGenUI;

    public PdfGenApplication(PdfGenUI pdfGenUI) {
        this.pdfGenUI = pdfGenUI;
    }

    public static void main(String[] args) {
        SpringApplication.run(PdfGenApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        pdfGenUI.start();
    }
}