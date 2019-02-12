package am.aca.dbmigration.controllers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        MigrationEventHandler eventHandler = new MigrationEventHandler();
        executorService.submit(() -> migration(eventHandler));
    }

    private static void migration(MigrationEventHandler eventHandler) {
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(1000);
                eventHandler.notify("Message " + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class MigrationEventHandler {
    void notify(String event) {

    }
}