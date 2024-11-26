package com.jobtrackingtool.controller;

import java.time.LocalDate;
import com.jobtrackingtool.service.GoogleSheetsWriter;
import org.springframework.web.bind.annotation.*;
import java.time.format.DateTimeParseException;
import com.jobtrackingtool.utils.DateUtils;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/sheets")
@CrossOrigin(origins = "http://localhost:3000")
public class GoogleSheetsController {

    private final GoogleSheetsWriter googleSheetsWriter;

    public GoogleSheetsController(GoogleSheetsWriter googleSheetsWriter) {
        this.googleSheetsWriter = googleSheetsWriter;
    }

    @PostMapping("/add")
    public String addToSheet(@RequestBody SheetData data) {
        // Set default roleName and status if not provided
        if (data.getRoleName() == null || data.getRoleName().isEmpty()) {
            data.setRoleName("Software Developer");
        }
        if (data.getStatus() == null || data.getStatus().isEmpty()) {
            data.setStatus("Waiting");
        }

        // Handle date
        String formattedDate;
        try {
            if (data.getDate() == null || data.getDate().isEmpty()) {
                // Default to today's date
                LocalDate now = LocalDate.now();
                data.setDate(now.toString()); // ISO format
                formattedDate = DateUtils.formatDateWithOrdinal(now); // Format for display
            } else {
                // Parse input date in ISO format (yyyy-MM-dd)
                LocalDate parsedDate = LocalDate.parse(data.getDate().trim());
                formattedDate = DateUtils.formatDateWithOrdinal(parsedDate); // Format for display
                System.out.println(formattedDate);
            }
        } catch (DateTimeParseException e) {
            System.out.println("Date parsing error: " + e.getMessage());
            return "Invalid date format. Please use 'yyyy-MM-dd' (e.g., '2024-11-23').";
        }

        // Log the formatted date for debugging
        System.out.println("Formatted date: " + data.getDate());

        // Call the service to write data
        try {
            googleSheetsWriter.writeData(data.getRoleName(), data.getCompanyName(), data.getStatus(), data.getDate());
            return "Data written successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to write data to Google Sheets. Please try again later.";
        }
    }

    public static class SheetData {
        private String roleName;
        private String companyName;
        private String status;
        private String date;

        public String getRoleName() {
            return roleName;
        }

        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}