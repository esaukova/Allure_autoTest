package ru.netology.data;
import com.github.javafaker.Faker;
import lombok.Value;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private DataGenerator() {
    }



    public static String generateDate(int plusDays) {
        String date = LocalDate.now().plusDays(plusDays).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return date;
    }

    public static String generateCity(String locale) {
        var cities = new String[]{
                "Майкоп", "Горно-Алтайск", "Уфа", "Улан-Удэ", "Махачкала", "Донецк", "Магас", "Нальчик", "Элиста", "Черкесск",
                "Петрозаводск", "Сыктывкар", "Симферополь", "Луганск", "Йошкар-Ола", "Саранск", "Якутск", "Владикавказ", "Казань",
                "Кызыл", "Ижевск", "Абакан", "Грозный", "Чебоксары", "Барнаул", "Чита", "Петропавловск-Камчатский" ,"Краснодар",
                "Красноярск", "Пермь", "Владивосток", "Ставрополь", "Хабаровск", "Благовещенск", "Архангельск", "Астрахань", "Белгород",
                "Брянск", "Владимир", "Волгоград", "Вологда", "Воронеж", "Мелитополь", "Иваново", "Иркутск", "Калининград", "Калуга", "Кемерово",
                "Киров", "Кострома", "Курган", "Курск", "Липецк", "Магадан", "Москва", "Мурманск", "Нижний Новгород", "Великий Новгород", "Новосибирск",
                "Омск", "Оренбург", "Орёл", "Пенза", "Псков", "Ростов-на-Дону", "Рязань", "Самара", "Саратов", "Южно-Сахалинск", "Екатеринбург",
                "Смоленск", "Тамбов", "Тверь", "Томск", "Тула", "Тюмень", "Ульяновск", "Херсон", "Челябинск", "Ярославль", "Санкт-Петербург", "Севастополь",
                "Биробиджан", "Нарьян-Мар", "Ханты-Мансийск", "Анадырь", "Салехард"
        };
        return cities[new Random().nextInt(cities.length)] ;
    }

    public static String generateName(String locale) {

        Faker faker = new Faker(new Locale(locale));
        String name = faker.name().firstName() + " " + faker.name().lastName();
        return name;
    }

    public static String generatePhone(String locale) {

        Faker faker = new Faker(new Locale(locale));
        String phone = faker.phoneNumber().phoneNumber();
        return phone;
    }

    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateUser(String locale) {

            UserInfo user = new UserInfo(generateCity(locale), generateName(locale), generatePhone(locale));
            return user;
        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }
}

