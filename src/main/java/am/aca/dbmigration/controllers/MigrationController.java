package am.aca.dbmigration.controllers;

import java.util.List;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

import am.aca.dbmigration.sql.MigrationData;
import am.aca.dbmigration.sql.SchemaAnalyzer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import am.aca.dbmigration.sql.generatedSQLs.GeneratedCreateSQLs;
import am.aca.dbmigration.sql.generatedSQLs.GeneratedInsertSQLs;
import am.aca.dbmigration.sql.generatedSQLs.GeneratedForeignSQls;
import am.aca.dbmigration.sql.generatedSQLs.GeneratedPrimarySQLs;
import org.springframework.web.context.request.RequestContextHolder;

@SessionScope
@RestController
@RequestMapping("/migrate")
public class MigrationController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public MigrationController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping("/err")
    public ResponseEntity<?> prepare() {
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        executorService.submit(() -> {
            try {
                preparation(sessionId);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        return ResponseEntity.ok().build();
    }

    @PostMapping("/migr")
    public ResponseEntity<?> migrate() {
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        executorService.submit(() -> {
            try {
                migration(sessionId);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        return ResponseEntity.ok().build();
    }

    private void migration(String sessionId) throws SQLException {


        Connection connection = DriverManager.getConnection(
                MigrationData.urlTo,
                MigrationData.usernameTo,
                MigrationData.passwordTo
        );

        Statement statement = connection.createStatement();

        for (String s : GeneratedCreateSQLs.getCreateSQLs()) {
            statement.addBatch(s);
        }
        statement.executeBatch();
        statement.clearBatch();
        messagingTemplate.convertAndSend("/message/" + sessionId, "Skeleton created");

        for (String s : GeneratedPrimarySQLs.getPrimarySQLs()) {
            statement.addBatch(s);
        }
        statement.executeBatch();
        statement.clearBatch();
        messagingTemplate.convertAndSend("/message/" + sessionId, "Primary keys set");

        for (String s : GeneratedInsertSQLs.getGeneratedInsertSQLs()) {
            statement.addBatch(s);
        }
        statement.executeBatch();
        statement.clearBatch();
        messagingTemplate.convertAndSend("/message/" + sessionId, "Data inserted");

        for (String s : GeneratedForeignSQls.getForeignSQLs()) {
            statement.addBatch(s);
        }
        statement.executeBatch();
        statement.clearBatch();
        messagingTemplate.convertAndSend("/message/" + sessionId, "Foreign keys set");

        messagingTemplate.convertAndSend("/message/" + sessionId, "done");
    }

    private void preparation(String sessionId) throws SQLException {
        List<String> strings = SchemaAnalyzer.generateSqls();
        messagingTemplate.convertAndSend("/message/" + sessionId, "Warning: " + "Indexes aren't supported");
        for (String s : strings) {
            messagingTemplate.convertAndSend("/message/" + sessionId, "Warning: " + s);
        }
        messagingTemplate.convertAndSend("/message/" + sessionId, "done");

    }


}