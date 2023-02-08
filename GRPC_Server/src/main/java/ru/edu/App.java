package ru.edu;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;

/**
 * Hello world!
 */
public class App {

  public static void main(String[] args) throws IOException, InterruptedException {
    // Встроенный сервер grpc
    Server server = ServerBuilder
        .forPort(8080)
        // добавляем все сервисы запросов
        .addService(new GreetingServiceImp())
        .build();

    server.start();
    System.out.println("Server started");

    server.awaitTermination();
  }

}
