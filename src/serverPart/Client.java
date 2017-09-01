package serverPart;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client implements Runnable {

    private Server server;
    private Socket socket;

    public Socket getSocket() {
        return socket;
    }

    private int number;

    private ObjectOutputStream output;
    private ObjectInputStream input;

    public Client(Server server, Socket socket, int number) {
        this.number = number;
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            output = (ObjectOutputStream) socket.getOutputStream();
            input = (ObjectInputStream) socket.getInputStream();

            System.out.println("Client #" + number + " was connected");

            while(true) {
                try {
                    Message message = (Message) input.readObject();
                    server.sendMessage(message, number);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
