package Lab6Client;


import java.io.Serializable;

/**
 * Класс - прототип команды, который будет отправлен на сервер для обработки.
 * Содержит поля:
 * commandName - имя команды
 * id - id элемента коллекции
 * element - объект коллекции
 * filename - имя файла для execute_script
 */
public class Command implements Serializable {

    private String commandName;
    private int id;
    private Flat element;
    private String filename;
    private static final long serialVersionUID = 1L;
    public String getCommandName() {
        return commandName;
    }

   public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Flat getElement() {
        return element;
    }

    public void setElement(Flat element) {
        this.element = element;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
