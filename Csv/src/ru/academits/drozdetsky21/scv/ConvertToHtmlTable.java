package ru.academits.drozdetsky21.scv;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ConvertToHtmlTable {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Use parameters: [Input File Name] [Output File Name]");

            return;
        }

        try (Scanner scanner = new Scanner(new FileInputStream(args[0]));
             PrintWriter writer = new PrintWriter(args[1])) {
            writer.print("<!DOCTYPE html><html><head><title>HTML Table</title><meta charset=\"utf-8\" /></head><body><table border=\"1\">");

            while (scanner.hasNextLine()) {
                writer.print("<tr><td>");
                String inputString = scanner.nextLine();
                boolean isInsideQuotationMarks = false;
                int i = 0;

                while (i < inputString.length() || isInsideQuotationMarks) {
                    if (i == inputString.length()) {
                        if (scanner.hasNextLine()) {
                            writer.print("<br/>");
                            inputString = scanner.nextLine();
                            i = 0;
                        } else {
                            break;
                        }
                    }

                    if (inputString.charAt(i) == '&') {
                        writer.print("&amp;");
                    } else if (inputString.charAt(i) == '<') {
                        writer.print("&lt;");
                    } else if (inputString.charAt(i) == '>') {
                        writer.print("&gt;");
                    } else if (inputString.charAt(i) == ',') {
                        if (isInsideQuotationMarks) {
                            writer.print(",");
                        } else {
                            writer.print("</td><td>");
                        }
                    } else if (inputString.charAt(i) == '"') {
                        if (!isInsideQuotationMarks) {
                            isInsideQuotationMarks = true;
                        } else {
                            if (i + 1 < inputString.length() && inputString.charAt(i + 1) == '"') {
                                writer.print("\"");
                                i++;
                            } else {
                                isInsideQuotationMarks = false;
                            }
                        }
                    } else {
                        writer.print(inputString.charAt(i));
                    }

                    i++;
                }

                writer.println("</td></tr>");
            }

            writer.print("</table></body></html>");
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
    }
}
