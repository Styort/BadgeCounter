package com.company;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.util.ArrayList;

public class Main {
    private ArrayList<Users> users = new ArrayList<>();
    private FileInputStream badgeData = new FileInputStream(new File(System.getProperty("user.home") + "/Desktop/report_Badges.xlsx"));
    private XSSFWorkbook workbookBadge = new XSSFWorkbook(badgeData);
    private XSSFSheet sheet = workbookBadge.getSheetAt(0);
    private String path = System.getProperty("user.home") + "/Desktop/finalBadgeCounter.xls";
    private int countPovtor = 0;
    private int k = 0;

    public Main() throws IOException {
    }

    public static void main(String[] args) throws IOException {
        Main main = new Main();
        main.DataAnalyse();
        main.createFinalExcel(main.path);
        System.out.println(main.countPovtor);

    }

    private void DataAnalyse() throws IOException {
        XSSFRow row;
        int count = 1;
        int check = 0;

        for (int j = 0; j < sheet.getPhysicalNumberOfRows()-1; j++) {
            row = sheet.getRow(count++);
            String login = row.getCell(1).toString(); //имя пользователя
            String mail;
            if (row.getCell(6) == null) { //мейл
                mail = " ";
            } else {
                mail = row.getCell(6).toString();
            }
            check++;
            if(userIsLeader(login, row.getCell(20).toString())){
                System.out.println("Найдено ! " + (check+1));
                XSSFCellStyle style = workbookBadge.createCellStyle();
                style.setFillForegroundColor(IndexedColors.RED.getIndex());
                style.setFillPattern(CellStyle.SOLID_FOREGROUND);
                row.getCell(0).setCellStyle(style);
                countPovtor++;
            }else {
                String badge = row.getCell(24).toString();
                Users newUser = new Users(login,mail,0,0,0,0,0);

                if (users.size() != 0) {
                    for (int i=k; i < users.size(); i++) {
                        if (!users.get(i).getLogin().equals(newUser.getLogin())) {
                            users.add(newUser);
                            k++;
                        }else {
                            switch (badge){
                                case "1061.0": //Гуру
                                    users.get(i).incBadge1();
                                    break;
                                case "1062.0": //Стратег
                                    users.get(i).incBadge3();
                                    break;
                                case "1063.0": //Мотиватор
                                    users.get(i).incBadge4();
                                    break;
                                case "1064.0": //Партнер
                                    users.get(i).incBadge2();
                                    break;
                                case "1065.0": //Энерджайзер
                                    users.get(i).incBadge5();
                                    break;
                            }
                        }
                    }
                } else {
                    users.add(newUser);
                }
            }

        }
        FileOutputStream out = new FileOutputStream(new File(System.getProperty("user.home") + "/Desktop/report_Badges1.xlsx"));
        workbookBadge.write(out);
        workbookBadge.close();
    }

    private void createFinalExcel(String file) throws IOException {
        Workbook book = new HSSFWorkbook();
        Sheet sheet = book.createSheet("Badges");

        // Нумерация начинается с нуля
        Row row = sheet.createRow(0);

        Cell userLogin = row.createCell(0);
        userLogin.setCellValue("Имя пользователя");
        Cell userEmail = row.createCell(1);
        userEmail.setCellValue("email");
        Cell energizerBadge = row.createCell(2);
        energizerBadge.setCellValue("Гуру");
        Cell partnerBadge = row.createCell(3);
        partnerBadge.setCellValue("Стратег");
        Cell strategistBadge = row.createCell(4);
        strategistBadge.setCellValue("Мотиватор");
        Cell motivatorBadge = row.createCell(5);
        motivatorBadge.setCellValue("Партнер");
        Cell lastBadge = row.createCell(6);
        lastBadge.setCellValue("Энерджайзер");

        //Row lastRow = sheet.createRow(sheet.getLastRowNum() + 1);  //находим последний заполненный ряд и записываем данные на следующий.
        // Меняем размер столбца
        sheet.autoSizeColumn(1);

        for (int i = 1; i < users.size(); i++) {
            row = sheet.createRow(i);

            //записываем именя пользователей
            userLogin = row.createCell(0);
            userLogin.setCellValue(users.get(i-1).getLogin());

            //записываем мейлы пользователей
            userEmail = row.createCell(1);
            userEmail.setCellValue(users.get(i-1).getEmail());

            energizerBadge = row.createCell(2);
            energizerBadge.setCellValue(users.get(i-1).getBadge1());

            partnerBadge = row.createCell(3);
            partnerBadge.setCellValue(users.get(i-1).getBadge2());

            strategistBadge = row.createCell(4);
            strategistBadge.setCellValue(users.get(i-1).getBadge3());

            motivatorBadge = row.createCell(5);
            motivatorBadge.setCellValue(users.get(i-1).getBadge4());

            lastBadge = row.createCell(6);
            lastBadge.setCellValue(users.get(i-1).getBadge5());

        }
        // Записываем всё в файл
        book.write(new FileOutputStream(file));
        book.close();
    }

    //проверка, является ли данный сотрудник, подчиненным того, кому он отдал бейдж
    private boolean userIsLeader(String whoTook, String whoGave) {
        XSSFRow row = null;

        //ищу номер в списке того, кто подарил бэйдж
        for (int i = 1; i < sheet.getPhysicalNumberOfRows() ; i++) {
            row = sheet.getRow(i);
            if(row.getCell(1).toString().equals(whoGave)){
                break;
            }
        }
        if (row.getCell(2).toString().equals(whoTook)) {
            return true;
        } else {
            return false;
        }
    }
}
