package com.actividadunidad1.chat;
import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class ChatServer {
    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        ServerSocket serverSocket = new ServerSocket(PORT);

        System.out.println("Servidor iniciado. Esperando clientes...");

        try {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                pool.execute(new ClientHandler(clientSocket));
            }
        } finally {
            serverSocket.close();
        }
    }

    static class ClientHandler implements Runnable {
        private Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                // Enviar opciones solo una vez
                out.println("Bienvenido al ChatBot. Elige una opción:\n1. Opción A\n2. Opción B\n3. Opción C\n4. Opción D\n5. Salir");

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println("Recibido del cliente: " + inputLine); // Mensaje de depuración
                    switch (inputLine) {
                        case "1":
                            out.println("Has elegido la opción A");
                            break;
                        case "2":
                            out.println("Has elegido la opción B");
                            break;
                        case "3":
                            out.println("Has elegido la opción C");
                            break;
                        case "4":
                            out.println("Has elegido la opción D");
                            break;
                        case "5":
                            out.println("Adiós!");
                            return; // Salir del método run, cerrando el hilo
                        default:
                            out.println("Opción no válida. Por favor, elige entre 1 y 5.");
                            break;
                    }
                }
            } catch (IOException e) {
                System.out.println("Error al manejar la conexión del cliente: " + e.getMessage());
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    System.out.println("Error al cerrar el socket: " + e.getMessage());
                }
            }
        }
    }
}

