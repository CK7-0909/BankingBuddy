package com.example.demo;

import java.io.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class ExcelReader {

    public List<User> readExcelData() throws IOException {

        //Checks for exceptions

        // Reading file from local directory
        InputStream file = ExcelReader.class.getClassLoader().getResourceAsStream("dataset.csv");
        if (file == null) {
            System.out.println("File not found in resources folder");
            return null;
        }


     /*       XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);*/
        List<User> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                //System.out.println("Value: " + values[2]);
                User user = new User(values[0], values[1], Date.valueOf(values[2]), Double.parseDouble(values[3]), values[4], Integer.valueOf(values[5]));
                users.add(user);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return users;
    }
}

