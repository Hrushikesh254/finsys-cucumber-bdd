package com.finsys.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.File;
import java.io.FileWriter;

public class PDFReader {
    public static void main(String[] args) {
        try {
            File file = new File("E:/Antigravity/Gmail - Fwd_ REQUIREMENT UNDERSTANDING.pdf");
            PDDocument document = PDDocument.load(file);
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            FileWriter writer = new FileWriter("target/pdf_content.txt");
            writer.write(text);
            writer.close();
            System.out.println("Written to target/pdf_content.txt");
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
