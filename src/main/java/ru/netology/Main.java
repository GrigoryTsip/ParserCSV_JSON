package ru.netology;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Main {

    public static String[] colomnMapping = {"id", "firstName", "lastName", "country", "age"};
    public static String fileName = "data.csv";

    public static void main(String[] args) throws IOException {

        // Из файла data.csv создаем объекты класса Employee
        List<Employee> staff = parseCSV(colomnMapping, fileName);

        System.out.println("Из CSV-файла data.csv создано экземпляров класса Employee: " + staff.size()
                + "\n\nЭкземпляры класса:");
        staff.forEach(System.out :: println);

        // Объекты класса Employee конвертируем в формат JSON и записываем в файл data.json
        String json = listToJson(staff);
        writeString(json, "data.json");

        System.out.println("\nВсе объекты класса Employee конвертированы в объекты JSON " +
                "\nв файле data.json, расположенном в корневой папке проекта");
    }

    public static List<Employee> parseCSV(String[] colomn, String fileName) {

        List<Employee> staff = null;

        try (CSVReader rcsv = new CSVReader(new FileReader(fileName))) {
            ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(Employee.class);
            strategy.setColumnMapping(colomn);

            CsvToBean<Employee> csv = new CsvToBeanBuilder<Employee>(rcsv)
                    .withMappingStrategy(strategy)
                    .build();
            staff = csv.parse();
            return staff;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return staff;
    }

    public static String listToJson(List<Employee> list) {

        GsonBuilder bldr = new GsonBuilder();
        Gson gson = bldr
                .setPrettyPrinting()
                .create();
        return gson.toJson(list);
    }

    public static void writeString(String json, String fileName) {

             try (FileWriter file = new FileWriter(fileName)) {

                file.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}