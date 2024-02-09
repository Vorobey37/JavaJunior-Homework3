package org.example;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/*Разработайте класс Student с полями String name, int age, transient double GPA (средний балл).
Обеспечьте поддержку сериализации для этого класса.
Создайте объект класса Student и инициализируйте его данными.
Сериализуйте этот объект в файл.
Десериализуйте объект обратно в программу из файла.
Выведите все поля объекта, включая GPA, и ответьте на вопрос,
почему значение GPA не было сохранено/восстановлено.

2. * Выполнить задачу 1 используя другие типы сериализаторов (в xml и json документы).*/
public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        /*task1*/
        Student student = new Student("Alexey", 39, 4.85);
        System.out.println(student);
        System.out.println(serializeToBib(student));
        //значение GPA не было сохранено/восстановлено потому, что оно помечено ключевым
        //словом transient, для указания, что поле класса не должно быть сериализовано
        // при сохранении объекта данного класса


        /*task2*/
        System.out.println(serializeToXml(student));
        System.out.println(serializeToJson(student));

    }

    public static Student serializeToBib(Student student) throws IOException, ClassNotFoundException {

        try (FileOutputStream fileOut = new FileOutputStream("StudentInfo.bin");
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut))
        {
            objectOut.writeObject(student);
        }

        try (FileInputStream fileIn = new FileInputStream("StudentInfo.bin");
             ObjectInputStream objectIn = new ObjectInputStream(fileIn))
        {
            student = (Student) objectIn.readObject();
        }
        return student;
    }

    public static Student serializeToXml(Student student) throws IOException {

        File xmlFile = new File("StudentInfo.xml");
        XmlMapper xmlMapper = new XmlMapper();

        xmlMapper.configure(SerializationFeature.INDENT_OUTPUT, true); //для красоты просмотра файла
        xmlMapper.writeValue(xmlFile, student);

        String readContent = new String(Files.readAllBytes(Paths.get("StudentInfo.xml")));
        xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        student = xmlMapper.readValue(readContent, Student.class);

        return student;
    }

    public static Student serializeToJson(Student student) throws IOException {

        File jsonFile = new File("StudentInfo.json");
        ObjectMapper jsonMapper = new ObjectMapper();

        jsonMapper.configure(SerializationFeature.INDENT_OUTPUT, true); //для красоты просмотра файла
        jsonMapper.writeValue(jsonFile, student);

        String readContent = new String(Files.readAllBytes(Paths.get("StudentInfo.json")));
        jsonMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        student = jsonMapper.readValue(readContent, Student.class);

        return student;
    }
}