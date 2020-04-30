package UserInterface;

import Lab6Client.*;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Класс, отвечаюший за проверку вводимых данных и создание экземпляра Flat.
 * Все методы fill* - отвечают за ввод с клавиатуры поля *.
 */
class Validation {
    /**
     * Метод отвечающий за создание экземпляра Flat.
     * @return new Flat
     */
    Flat constructFlat(){
        Scanner cin = new Scanner(System.in);
        Flat flat = new Flat();
        flat.setName(fillName(cin));
        flat.setId(0);
        flat.setCoordinates(fillCoordinates(cin));
        flat.setArea(fillArea(cin));
        flat.setNumberOfRooms(fillNumberOfRooms(cin));
        flat.setFurniture(fillFurniture(cin));
        flat.setView(fillView(cin));
        flat.setTransport(fillTransport(cin));
        flat.setHouse(fillHouse(cin));
        return flat;
    }
    private String fillName(Scanner cin) {
        System.out.println("Какое название у квартиры:");
        String name = cin.nextLine();
        try {
            if (name == null || name.equals(""))
                throw new InputMismatchException();
        } catch (InputMismatchException inputExceptions) {
            System.out.println("Некорректный ввод, введите название заново!");
            fillName(cin);
        }
        return name;
    }

    private Coordinates fillCoordinates(Scanner cin) throws InputMismatchException {
        try {
            System.out.println("Введите координату X квартиры: " + "(Она должна быть больше -897)");
            int coordinateX = cin.nextInt();
            System.out.println("Введите координату У квартиры: ");
            double coordinateY = cin.nextDouble();
            if (coordinateX <= -897 || Integer.valueOf(coordinateX).equals(-0)) {
                throw new IOException();
            }
            return new Coordinates(coordinateX, coordinateY);
        } catch (IOException e) {
            System.out.println("Некорректный ввод. Введите координаты заново!");
            fillCoordinates(cin);
            return null;
        }
        catch (InputMismatchException e){
            System.out.println("Некорректный ввод. Введите координаты заново!");
            cin.nextLine();
            fillCoordinates(cin);
            return null;
        }
    }

    private long fillArea(Scanner cin) {
        System.out.println("Введите area квартиры: (area должна быть больше 0)");
        try {
            long area = cin.nextLong();
            if (area <= 0)
                throw new IOException();
            return area;
        } catch (IOException e) {
            System.out.println("Некорректный ввод, введите заново!");
            fillArea(cin);
            return 0;
        }
        catch (InputMismatchException e){
            System.out.println("Некорректный ввод, введите заново!");
            cin.nextLine();
            fillArea(cin);
            return 0;
        }
    }

    private Integer fillNumberOfRooms(Scanner cin) {
        System.out.println("Сколько комнат в квартире: (Число комнат должно быть больше 0)");
        try {
            int nof = cin.nextInt();
            if (nof <= 0)
                throw new IOException();
            return nof;
        } catch (IOException e) {
            System.out.println("Некорректный ввод, введите число комнат заново!");
            fillNumberOfRooms(cin);
            return 0;
        }
        catch (InputMismatchException e){
            System.out.println("Некорректный ввод, введите число комнат заново!");
            cin.nextLine();
            fillNumberOfRooms(cin);
            return 0;
        }
    }

    private boolean fillFurniture(Scanner cin) {
        try {
            System.out.println("Введите мебель, которая находится в квартире: (True/False)");
            return cin.nextBoolean();
        }
        catch (InputMismatchException e){
            System.out.println("Неверный ввод, введите значение поля мебель заново!");
            //cin.skip(cin.nextLine());
            cin.nextLine();
            fillFurniture(cin);
            return false;
        }
    }

    private View fillView(Scanner cin) {
        System.out.println("Введите вид, который открывается из окон квартиры: ");
        System.out.println("Список доступных вариантов: ");
        for (View s : View.values()) {
            System.out.println(s);
        }
        try {
            return View.valueOf(cin.next());
        } catch (InputMismatchException | IllegalArgumentException e) {
            System.out.println("Некорректный ввод, введите заново!");
            cin.nextLine();
            fillView(cin);
            return null;
        }
    }

    private Transport fillTransport(Scanner cin) {
        System.out.println("Введите один из вариантов количества транспорта, который проезжает мимо квратиры: ");
        System.out.println("Список доступных вариантов: ");
        for (Transport s : Transport.values()) {
            System.out.println(s);
        }
        try {
            return Transport.valueOf(cin.next());
        } catch (InputMismatchException | IllegalArgumentException e) {
            System.out.println("Некорректный ввод, введите заново!");
            cin.nextLine();
            fillTransport(cin);
            return null;
        }
    }

    private House fillHouse(Scanner cin) {
        try {
            System.out.println("Необходимо ввести данные дома, в к котором находится квартира.");
            System.out.println("Введите название дома: ");
            String name = cin.next();
            System.out.println("Введите год, в котором дом был построен: (год должен быть больше 0)");
            long year = cin.nextLong();
            System.out.println("Введите число лифтов, находящихся в доме: (число лифтов должно быть больше 0" );
            int lifts = cin.nextInt();
            House house = new House(name, year, lifts);
            if (name == null || year <= -0 || lifts <= -0 || name.equals(""))
                throw new IOException();
            return house;
        } catch (IOException e) {
            System.out.println("Некорректный ввод, введите данные для дома заново!");
            cin.nextLine();
            fillHouse(cin);
            return null;
        }
        catch (InputMismatchException e){
            System.out.println("Некорректный ввод, введите заново!");
            cin.nextLine();
            fillHouse(cin);
            return null;
        }
    }
}
