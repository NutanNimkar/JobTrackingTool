package com.jobtrackingtool.service;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.jobtrackingtool.utils.DateUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import com.jobtrackingtool.utils.DateUtils;
import java.time.format.DateTimeParseException;
import java.time.format.DateTimeFormatter;

@Service
public class GoogleSheetsWriter {
    private static final String SPREADSHEET_ID = "1_gVAHO0G6nFvlW62bb9yUJ0j7VDj9sp2_Q-JjYW1CfY";
    private static final String RANGE = "Sheet1!A142:D";
    private static final List<String> VALID_STATUS = Arrays.asList("Waiting", "Response", "Interviewing", "OA", "OFFER");

    public void writeData(String roleName, String companyName, String status, String date) {
        if (!VALID_STATUS.contains(status)) {
            System.out.println("Invalid status: " + status + ". Please use one of the valid options: " + VALID_STATUS);
            return;
        }

        try {
            // Set default values for roleName and status
            if (roleName == null || roleName.isEmpty()) {
                roleName = "Software Developer";
            }
            if (status == null || status.isEmpty()) {
                status = "Waiting";
            }

            // Parse and format the date
            LocalDate dateObj;
            if (date == null || date.isEmpty()) {
                dateObj = LocalDate.now(); // Default to today's date
            } else {
                try {
                    // First, try parsing as ISO format (yyyy-MM-dd)
                    dateObj = LocalDate.parse(date);
                } catch (DateTimeParseException e) {
                    // If ISO parsing fails, fallback to dd-MM-yyyy
                    DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    dateObj = LocalDate.parse(date, inputFormatter);
                }
            }

            // Format the date for Google Sheets
            String formattedDate = DateUtils.formatDateWithOrdinal(dateObj);

            // Connect to Google Sheets and write data
            GoogleSheetsService service1 = new GoogleSheetsService();
            Sheets sheetsService = service1.getSheetsService();

            ValueRange response = sheetsService.spreadsheets().values()
                    .get(SPREADSHEET_ID, RANGE)
                    .execute();

            List<List<Object>> values = response.getValues();
            int startRow = 142;
            int nextEmptyRow = findNextEmptyRow(values, startRow);

            if (nextEmptyRow == -1) {
                System.out.println("No empty rows available in the range.");
                return;
            }

            String targetRange = "Sheet1!A" + nextEmptyRow + ":D";

            ValueRange body = new ValueRange().setValues(
                    Arrays.asList(
                            Arrays.asList(roleName, companyName, status, formattedDate)
                    )
            );

            sheetsService.spreadsheets().values()
                    .append(SPREADSHEET_ID, targetRange, body)
                    .setValueInputOption("RAW")
                    .execute();

            System.out.println("Data written successfully to row " + nextEmptyRow + ".");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use 'dd-MM-yyyy' or 'yyyy-MM-dd'.");
        }
    }

    private static int findNextEmptyRow(List<List<Object>> values, int startRow) {
        if (values == null || values.isEmpty()) {
            return startRow;
        }
        for (int i = 0; i < values.size(); i++) {
            List<Object> row = values.get(i);
            if (row.isEmpty() || (row.size() < 4 && row.stream().allMatch(cell -> cell == null || cell.toString().isEmpty()))) {
                return startRow + i;
            }
        }
        return startRow + values.size();
    }
}
