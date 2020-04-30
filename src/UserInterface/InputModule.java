package UserInterface;

import Lab6Client.Command;
import Lab6Client.Flat;
import Lab6Client.Invoker;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * Класс, реализующий подключение к серверу, отправку команд и чтение ответа.
 */
class InputModule {
    private static final int PORT = 5558;
    private static final String ADDRESS = "127.0.0.1";
    private Validation validation = new Validation();
    private Invoker invoker;
    private Socket socket;
    private boolean connectionFlag = false;

    InputModule(Invoker invoker) {
        this.invoker = invoker;
    }

    /**
     * Метод, отвечающий за подключение к серверу.
     * Устанавливает флаг connectionFlag.
     * @throws IOException
     */
    private void connect() throws IOException {
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(ADDRESS, PORT));
            if (!connectionFlag)
            System.out.println("Подключение: " + socket.getRemoteSocketAddress() + " прошло успешно.");
            connectionFlag = true;
            System.out.println("Введите команду: ");
        } catch (java.net.ConnectException e) {
            System.out.println("Сервер временно недоступен.");
            System.out.println("Прекращаем работу программы.");
            System.exit(0);
        }
    }

    /**
     * Метод, реализуюший запуск клиентского приложения.
     * Отвечает за выбор команды по вводу пользователя, отправку и чтение ответа.
     * @throws IOException
     */
    void run() throws IOException {
            connect();
            try {
                Scanner cin = new Scanner(System.in);
                while (cin.hasNext()) {
                    if (!socket.isConnected()) {
                        System.out.println("Сервер временно недоступен.");
                        System.out.println("Прекращаем работу программы.");
                        System.exit(0);
                    }
                    String[] args = cin.nextLine().split(" ");
                    Command element;
                    switch (args[0]) {
                        case "help": {
                            element = invoker.getCommandHashMap().get("help");
                            transerObj(socket, element);
                            break;
                        }
                        case "info":
                            element = invoker.getCommandHashMap().get("info");
                            write(socket, element);
                            read(socket);
                            break;
                        case "show":
                            element = invoker.getCommandHashMap().get("show");
                            transerObj(socket, element);
                            break;
                        case "add":
                            Flat flat = validation.constructFlat();
                            element = invoker.getCommandHashMap().get("add");
                            element.setElement(flat);
                            transerObj(socket, element);
                            break;
                        case "update":
                            int id = Integer.parseInt(args[1]);
                            Flat flat2 = validation.constructFlat();
                            element = invoker.getCommandHashMap().get("update");
                            element.setElement(flat2);
                            element.setId(id);
                            transerObj(socket, element);;
                            break;
                        case "remove_by_id":
                            element = invoker.getCommandHashMap().get("remove_by_id");
                            element.setId(Integer.parseInt(args[1]));
                            transerObj(socket, element);
                            break;
                        case "clear":
                            element = invoker.getCommandHashMap().get("clear");
                            transerObj(socket, element);
                            break;
                        case "execute_script":
                            element = invoker.getCommandHashMap().get("execute_script");
                            element.setFilename(args[1]);
                            transerObj(socket, element);
                            break;
                        case "exit":
                            System.out.println("Завершение программы.");
                            System.exit(0);
                            break;
                        case "add_if_max":
                            Flat flat1 = validation.constructFlat();
                            element = invoker.getCommandHashMap().get("add_if_max");
                            element.setElement(flat1);
                            transerObj(socket, element);
                            break;
                        case "remove_greater":
                            Flat flat3 = validation.constructFlat();
                            element = invoker.getCommandHashMap().get("remove_greater");
                            element.setElement(flat3);
                            transerObj(socket, element);
                            break;
                        case "history":
                            element = invoker.getCommandHashMap().get("history");
                            transerObj(socket, element);
                            break;
                        case "min_by_furniture":
                            element = invoker.getCommandHashMap().get("min_by_furniture");
                            transerObj(socket, element);
                            break;
                        case "group_counting_by_transport":
                            element = invoker.getCommandHashMap().get("group_counting_by_transport");
                            transerObj(socket, element);
                            break;
                        case "print_field_descending_number_of_rooms":
                            element = invoker.getCommandHashMap().get("print_field_descending_number_of_rooms");
                            transerObj(socket, element);
                            break;
                        default:
                            System.out.println("\"Вы ввели неверную команду! Для того, чтобы увидеть список команд введите \"help\".");
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Команда введена некорректно и не может быть отправлена, необходимо наличие аргумента.");
                run();
            }
    }

    /**
     * Метод для отправки команды на сервер.
     * @param socket - socket, по которому установлено соединеие с сервером.
     * @param command - команда, которая будет отправлена на сервер.
     */
    private void write(Socket socket, Command command) {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
            bos.write(castToByteArray(command), 0, castToByteArray(command).length);
            bos.flush();
        } catch (IOException e) {
            System.out.println("Сервер временно недоступен.");
            System.out.println("Завершаем программу.");
            System.exit(0);
        }
    }

    /**
     * Метод для чтения ответа от сервера.
     * @param socket - получает на вход socket, который подключен к серверу.
     */
    private void read(Socket socket) {
        try {
            byte[] buffer = new byte[100 * 100];
            InputStream is = socket.getInputStream();
            int numRead = is.read(buffer);
            if (numRead != 0) {
                StringBuilder s = new StringBuilder();
                Scanner cin = new Scanner(new String(buffer));
                while (cin.hasNextLine()) {
                    s.append(cin.nextLine());
                    if (s.charAt(0) == ' ' || s.charAt(0) == '[')
                        s.deleteCharAt(0);
                    System.out.println(s.toString().replace("]", "").replace(",", "").trim());
                    s.delete(0, s.length());
                }
                if (socket.isConnected()) {
                    System.out.println("Введите команду: ");
                } else {
                    System.out.println("Произошёл сбой соединения.");
                    System.out.println("Завершение программы.");
                    System.exit(0);
                }
            }
        } catch (IOException e) {
            System.out.println("Соединение с сервером разорвано.");
            System.out.println("Завершение программы.");
            System.exit(0);
        }
    }

    /**
     * Метод для упаковки команды в массив байт.
     * @param command - принимает команду
     * @return команда в виде массива байтов.
     * @throws IOException I/O excepiton, который будет пойман в методе write, если будет выброшен
     */
    private byte[] castToByteArray(Command command) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(command);
        return baos.toByteArray();
    }
    private void transerObj(Socket socket, Command command){
        write(socket, command);
        read(socket);
    }
}
