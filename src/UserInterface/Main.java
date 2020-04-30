package UserInterface;

import Lab6Client.Invoker;

import java.io.IOException;

/**
 * Точка входа, запускающая работу клиентского приложения.
 */

public class Main {

    public static void main(String[] args) throws IOException {
        Invoker invoker = new Invoker();
        InputModule inputModule = new InputModule(invoker);
        inputModule.run();
    }
}
