package com.yourmechanic;

import java.io.*;
import java.util.*;

/**
 * @author John Gillson
 * @since 08/2020
 */
public class DisjointedIntervalsProgram {

    /**
     * This method is the main entry point of the program.
     *
     * @param args the args passed in, if any
     */
    public static void main(String[] args) throws IOException {

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                System.out.println("> ");
                String line = bufferedReader.readLine();
                if (line.length() == 0) {
                    System.out.println("Done");
                    break;
                }

                Scanner scanner = new Scanner(line);
                String inputFile, outputFile;

                inputFile = scanner.next();
                outputFile = scanner.next();

                DisjointedIntervalsFileHandler.handleFiles(inputFile, outputFile);
            }
        }
        catch (NoSuchElementException e) {
            System.err.println("Syntax error");
            System.out.println();
        }
    }
}
