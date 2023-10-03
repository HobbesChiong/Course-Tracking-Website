package model;


import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

/*
    Reads a CSV file and each row becomes an index in listOfCsvRows
 */
public class CsvReader {
    String filename;
    private static final String SPLIT_STRING = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
    private final List<String[]> listOfCsvRows = new ArrayList<>();

    public CsvReader(String filename) {
        this.filename = filename;
        populateCSVFile(filename);
        removeHeaderArray();
    }

    private void populateCSVFile(String filename) {
        BufferedReader reader = null;
        String line = "";

        try {
            reader = new BufferedReader(new FileReader(filename));
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(SPLIT_STRING);
                String[] trimmedRow = new String[row.length];

                // Remove unnecessary characters
                for (int i = 0; i < row.length; i++) {
                    String trimmedWord = row[i].trim();
                    trimmedWord = trimmedWord.replace("\"", "");
                    trimmedRow[i] = trimmedWord;
                }

                listOfCsvRows.add(trimmedRow);
            }
        } catch (IOException e) {
            throw new RuntimeException();
        } finally {
            try {
                Objects.requireNonNull(reader).close();
            } catch (IOException e) {
                throw new RuntimeException();
            }
        }
    }

    private void removeHeaderArray() {
        this.listOfCsvRows.remove(0);
    }

    public List<String[]> getListOfCsvRows() {
        return this.listOfCsvRows;
    }
}
