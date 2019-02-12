package am.aca.dbmigration.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.SessionScope;

import java.util.List;
import java.util.concurrent.*;

@SessionScope
@RestController("/api")
public class MigrationController {

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @RequestMapping(value = "/migrate", method = RequestMethod.POST)
    public ResponseEntity<?> migrate(@RequestBody MigrationData migrationData) {
        executorService.submit(() -> doMigrate(migrationData));
        return ResponseEntity.ok().build();
    }

    private void doMigrate(MigrationData migrationData) {

    }

}

class MigrationData {
    private String targetUrl;
    private String username;
    private String password;
    private List<String> tables;
}
