package com.yourmechanic;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author John Gillson
 * @since 08/2020
 */
class DisjointedIntervalsFileHandler {

    /**
     * This method handles parsing the CSV file as input and outputs results to an output file
     *
     * @param inputFile the input file in CSV format
     * @param outputFile the output file in txt format
     */
    static void handleFiles(String inputFile, String outputFile) {
        try (Reader reader = Files.newBufferedReader(Paths.get(inputFile));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
             FileOutputStream fos = new FileOutputStream(outputFile);
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fos))) {

            for (CSVRecord csvRecord : csvParser) {
                String action = DisjointedIntervalsManager.parseActionCommand(csvRecord.get(0));
                int[] values = DisjointedIntervalsManager.parseOperationCommand(csvRecord.get(1), csvRecord.get(2));

                if (action.equals("add")) {
                    bufferedWriter.write(DisjointedIntervalsManager.add(values[0], values[1]).toString());
                    bufferedWriter.newLine();
                }
                else if (action.equals("remove")) {
                    bufferedWriter.write(DisjointedIntervalsManager.remove(values[0], values[1]).toString());
                    bufferedWriter.newLine();
                }
                else {
                    System.err.println("Not a valid command: " + action);
                }
            }
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            System.out.println();
        }
    }
}
