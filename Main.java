// Обучающийся: ШИТОВ Олег Владимирович, "Разработчик Python", поток 4544, будни, утро.  12.08.2023.
//
// Исключения в программировании и их обработка (семинары)

// Урок 3. Продвинутая работа с исключениями в Java

// Напишите приложение, которое будет запрашивать у пользователя следующие данные через пробел:
// фамилия имя отчество дата_рождения номер_телефона пол
// Форматы данных:
// фамилия, имя, отчество - строки
// датарождения - строка формата dd.mm.yyyy
// номертелефона - целое беззнаковое число без форматирования
// пол - символ латиницей f или m.
// Приложение должно проверить введенные данные по количеству.
// Если количество не совпадает с требуемым, вернуть код ошибки, обработать его и показать пользователю сообщение,
// что он ввел меньше и больше данных, чем требуется.
// Приложение должно попытаться распарсить полученные значения и выделить из них требуемые параметры.
// Если форматы данных не совпадают, нужно бросить исключение, соответствующее типу проблемы. Можно использовать встроенные типы java и создать свои. Исключение должно быть корректно обработано, пользователю выведено сообщение с информацией, что именно неверно.
// Если всё введено и обработано верно, то должен создаться файл с названием, равным фамилии,
//  в него в одну строку должны записаться полученные данные, вида:
// <Фамилия> <Имя> <Отчество> <дата_рождения> <номер_телефона> <пол>
// Однофамильцы должны записаться в один и тот же файл, в отдельные строки.
// Не забудьте закрыть соединение с файлом. 
// При возникновении проблемы с чтением-записью в файл, исключение должно быть корректно обработано,
// пользователь должен увидеть стектрейс ошибки.

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {

    private static boolean fileExists(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

    private static void appendToFile(String fileName, String[] data) {
        try {
            FileWriter fileWriter = new FileWriter(fileName, true);
            fileWriter.write(String.join(" ", data) + "\n");
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }

    private static void createFile(String fileName, String[] data) {
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(String.join(" ", data) + "\n");
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Ошибка при создании файла: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Введите данные через пробел: фамилия имя отчество дата_рождения номер_телефона пол. Выход - ввод пустой строки.");
            String input = scanner.nextLine();

            if (input.length() == 0) {
                System.err.println("Выход.");
                break;
            }
            String[] data = input.split(" ");
            if (data.length != 6) {
                System.err.println("Ошибка: количество данных не соответствует ожидаемому");
                return;
            }

            String lastName = data[0];
            String firstName = data[1];
            String middleName = data[2];
            LocalDate birthDate;
            try {
                birthDate = LocalDate.parse(data[3], java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            } catch (DateTimeParseException e) {
                System.err.println("Ошибка: неверный формат даты рождения");
                return;
            }
            long phoneNumber;
            try {
                phoneNumber = Long.parseLong(data[4]);
            } catch (NumberFormatException e) {
                System.err.println("Ошибка: неверный формат номера телефона");
                return;
            }
            String gender = data[5];

            if (!gender.equals("f") && !gender.equals("m")) {
                System.err.println("Ошибка: неверное значение пола");
                return;
            }

            String fileName = lastName + ".txt";

            if (fileExists(fileName)) {
                appendToFile(fileName, data);
            } else {
                createFile(fileName, data);
            }
            System.out.println("Данные успешно записаны в файл " + fileName);
        }
    }
}