package com.example.demo;

import java.io.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ExcelReader {

    public List<User> readExcelData(MultipartFile file) throws IOException {

        List<User> users = new ArrayList<>();
        InputStream inputStream = file.getInputStream();
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();

        // Skip header row
        if (rowIterator.hasNext()) {
            rowIterator.next();
        }

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            User user = new User();

            user.setfirstname(row.getCell(0).getStringCellValue());
            user.setlastname(row.getCell(1).getStringCellValue());
            user.setdob(row.getCell(2).getStringCellValue());
            user.setBankBalance(row.getCell(3).getNumericCellValue());
            user.setcountry(row.getCell(4).getStringCellValue());
            user.setcreditAge(Integer.parseInt(row.getCell(5).getStringCellValue()));
            users.add(user);
            }

        return users;
    }
}

