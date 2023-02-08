package ru.edu;

import io.grpc.stub.StreamObserver;
import ru.edu.grpc.GreetingServiceGrpc;
import ru.edu.grpc.GreetingServiceOuterClass;
import ru.edu.grpc.GreetingServiceOuterClass.HelloRequest;
import ru.edu.grpc.GreetingServiceOuterClass.HelloResponse;

// GreetingServiceGrpc.GreetingServiceImplBase генерится в target
public class GreetingServiceImp extends GreetingServiceGrpc.GreetingServiceImplBase {

  @Override
  public void greeting(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
    System.out.println(request);

    // отправляем данные в потоке
    for (int i = 0; i < 1000; i++) {
      try {
        Thread.sleep(100);

        // GreetingServiceOuterClass.HelloResponse генерится в target
        GreetingServiceOuterClass.HelloResponse response = GreetingServiceOuterClass
            .HelloResponse.newBuilder()
            .setGreeting("Hello from server, " + request.getName())
            .build();

        // пока отсылаем один ответ. Но можем передавать поток данных
        responseObserver.onNext(response);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    responseObserver.onCompleted();
  }

}
