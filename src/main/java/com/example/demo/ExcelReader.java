package com.example.demo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

@Component
public class ExcelReader {

    public List<User> readExcelData(MultipartFile file) throws IOException {

        List<User> users = new ArrayList<>();
        InputStreamReader inputStreamReader = new InputStreamReader(file.getInputStream());
        //Workbook workbook = new XSSFWorkbook(inputStream);
        //Sheet sheet = workbook.getSheetAt(0);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(inputStreamReader);

        for (CSVRecord record : records) {
            User user = new User();

            user.setfirstname(record.get("FirstName"));
            user.setlastname(record.get("LastName"));
            user.setdob(record.get("DOB"));
            user.setBankBalance(Double.parseDouble(record.get("BankBalance")));
            user.setcountry(record.get("Country"));
            user.setcreditAge(Integer.parseInt(record.get("CreditAge")));
            users.add(user);
            }

        return users;
    }
}

