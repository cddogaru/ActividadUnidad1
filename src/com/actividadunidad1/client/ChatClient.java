package com.actividadunidad1.client;

import java.io.*;
import java.net.*;

public class ChatClient {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        System.out.println("Intentando conectar al servidor en " + SERVER_ADDRESS + ":" + SERVER_PORT);

        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Conexión establecida con el servidor.");
            
            String fromServer;
            while ((fromServer = in.readLine()) != null) {
                System.out.println("Servidor: " + fromServer);
                if ("Adiós!".equals(fromServer)) {
                    System.out.println("Desconectando del servidor...");
                    break; // Salir del bucle para cerrar la conexión
                }

                System.out.print("Tu: ");
                String fromUser = stdIn.readLine();
                if (fromUser != null) {
                    System.out.println("Enviando al servidor: " + fromUser); // Mensaje de depuración
                    out.println(fromUser);
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("No se puede conectar al host: " + SERVER_ADDRESS);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Se ha producido un error de entrada/salida.");
            e.printStackTrace();
        } finally {
            System.out.println("Cliente cerrado.");
        }
    }
}
