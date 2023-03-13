package com.example.welcometoesprit.ServicesImpl;

import com.example.welcometoesprit.entities.Nationality;
import com.example.welcometoesprit.entities.Role;
import com.example.welcometoesprit.entities.User;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
@Service
public class ExcelUploadService {

    private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public static boolean isValidExcelFile(MultipartFile file){
        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" );
    }
    public static List<User> getUsersDataFromExcel(InputStream inputStream){
        List<User> users = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheet("users");
            int rowIndex =0;
            List<String> mails=new ArrayList<>();
            for (Row row : sheet){
                if (rowIndex ==0){
                    rowIndex++;
                    continue;
                }
                Iterator<Cell> cellIterator = row.iterator();
                int cellIndex = 0;
                User user = new User();
                String enabled="";
                while (cellIterator.hasNext()){
                    Cell cell = cellIterator.next();
                    switch (cellIndex){
                        case 0 -> user.setFirstname(cell.getStringCellValue());
                        case 1 -> user.setLastname(cell.getStringCellValue());
                        case 2 -> user.setEmail(cell.getStringCellValue());
                        case 3 -> user.setPassword(cell.getStringCellValue());
                        case 4 -> user.setRole(Role.valueOf(cell.getStringCellValue()));
                        case 5 -> user.setEnabled(true);
                        case 6 -> user.setCin(cell.getStringCellValue().substring(1,9));
                        case 7 -> user.setNationality(Nationality.valueOf(cell.getStringCellValue()));
                        default -> {
                        }
                    }
                    cellIndex++;
                //mails.add(user.getEmail());
                }
                if (user.getEmail()!=null){
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                users.add(user);}
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        return users;
    }

}
