package com.sudreeshya.sms.download.excel.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sudreeshya.sms.download.excel.service.ExcelService;
import com.sudreeshya.sms.response.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

/**
 * @author Manjit Shakya <manjit.shakya@f1soft.com>
 */
@Slf4j
@Service
public class UserPOIExcelServiceImpl implements ExcelService<List<UserDTO>, ByteArrayOutputStream> {

    @Override
    public ByteArrayOutputStream convert(List<UserDTO> input) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        final String json = objectMapper.writeValueAsString(input);

        JSONArray jsonArray = new JSONArray(json);

        File file = new File("D:\\template.xls");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        XSSFWorkbook workbook = readXlsTemplate(file);
        appendJsonToTemplate(workbook, jsonArray);
        writeWorkBookToOutputStream(workbook,outputStream);

        return outputStream;
    }

    private XSSFWorkbook readXlsTemplate(File file) throws Exception {
        FileInputStream inputStream = new FileInputStream(file);
        return new XSSFWorkbook(inputStream);
    }

    private OutputStream writeWorkBookToOutputStream(XSSFWorkbook hssfWorkbook, OutputStream outputStream) throws IOException {
        hssfWorkbook.write(outputStream);
        return outputStream;
    }

    private XSSFWorkbook appendJsonToTemplate(XSSFWorkbook workbook, JSONArray jsonArray) throws Exception {

        XSSFSheet sheet = workbook.getSheetAt(0);

        XSSFRow row0 = sheet.createRow(0);
        XSSFCell r1c0 = row0.createCell(0);
        r1c0.setCellValue("Id");

        XSSFRow row1 = sheet.createRow(1);
        XSSFCell r1c1 = row1.createCell(0);
        r1c1.setCellValue("Firstname");

        XSSFRow row2 = sheet.createRow(2);
        XSSFCell r1c2 = row2.createCell(0);
        r1c2.setCellValue("Lastname");

        XSSFRow row3 = sheet.createRow(3);
        XSSFCell r1c3 = row3.createCell(0);
        r1c3.setCellValue("Email Address");

        XSSFRow row4 = sheet.createRow(4);
        XSSFCell r1c4 = row4.createCell(0);
        r1c4.setCellValue("Contact No");


        String applicationUser = null;
        for(int i =0 ;i<jsonArray.length();i++) {
            applicationUser = jsonArray.get(i).toString();
            log.debug("Application User : " + applicationUser);

            JSONObject obj = new JSONObject(applicationUser);

            XSSFRow row00 = sheet.getRow(0);
            XSSFCell r1c00 = row00.createCell(i + 1);
            r1c00.setCellValue(obj.getLong("id"));
            XSSFRow row102 = sheet.getRow(1);
            XSSFCell r1c102 = row102.createCell(i + 1);
            r1c102.setCellValue(obj.getString("firstName"));
            XSSFRow row202 = sheet.getRow(2);
            XSSFCell r1c202 = row202.createCell(i + 1);
            r1c202.setCellValue(obj.getString("lastName"));
            XSSFRow row30 = sheet.getRow(3);
            XSSFCell r1c30 = row30.createCell(i + 1);
            r1c30.setCellValue(obj.getString("emailAddress"));
            XSSFRow row40 = sheet.getRow(4);
            XSSFCell r1c40 = row40.createCell(i + 1);
            r1c40.setCellValue(obj.getString("contactNo"));

        }
        return workbook;
    }
}
