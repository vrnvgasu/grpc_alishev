package ru.edu;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.util.Iterator;
import ru.edu.grpc.GreetingServiceGrpc;
import ru.edu.grpc.GreetingServiceOuterClass;

public class Client {

  public static void main(String[] args) {
    ManagedChannel channel = ManagedChannelBuilder
        .forTarget("localhost:8080") // адрес сервера, куда стучимся
        .usePlaintext()
        .build();

    // GreetingServiceGrpc сгенерен из .proto
    // на stub вызваются удаленные методы с сервера
    GreetingServiceGrpc.GreetingServiceBlockingStub stub =
        GreetingServiceGrpc.newBlockingStub(channel);
    GreetingServiceOuterClass.HelloRequest request =
        GreetingServiceOuterClass.HelloRequest.newBuilder()
            .setName("Name")
            .build();

    // пока сервер возвращает только один ответ
//    GreetingServiceOuterClass.HelloResponse response = stub.greeting(request);
//    System.out.println(response);

    // принимаем поток данных 
    Iterator<GreetingServiceOuterClass.HelloResponse> responseIterator = stub.greeting(request);
    while (responseIterator.hasNext()) {
      System.out.println(responseIterator.next());
    }

    channel.shutdown();
  }

}
