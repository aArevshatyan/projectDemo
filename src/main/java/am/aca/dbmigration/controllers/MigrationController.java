package am.aca.dbmigration.controllers;

import am.aca.dbmigration.sql.MigrationData;
import am.aca.dbmigration.sql.SchemaAnalyzer;
import am.aca.dbmigration.sql.generatedSQLs.*;
import am.aca.dbmigration.sql.tables.Table;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.context.request.RequestContextHolder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Controller for some processes during migration.
 * It is responsible for clearing queries when needed,
 * preparation process for migation,
 * and finally the migration process
 * All database long lasting processes are done by a single worker
 * thread operating off an unbounded queue.
 */
@SessionScope
@RestController
@RequestMapping("/migrate")
public class MigrationController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public MigrationController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping("/clear")
    public ResponseEntity<?> clear() {
        UnsupportedFeatures.emptyList();
        GeneratedCreateSQLs.emptyList();
        GeneratedPrimarySQLs.emptyList();
        GeneratedForeignSQls.emptyList();
        GeneratedInsertSQLs.emptyList();
        for (Table table : MigrationData.schemaFrom.getTables()) {
            table.setEnabled(false);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/err")
    public ResponseEntity<?> prepare() {
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        executorService.submit(() -> {
            try {
                preparation(sessionId);

            } catch (SQLException | InterruptedException e) {
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
            } catch (SQLException | InterruptedException e) {
                e.printStackTrace();
            }
        });
        return ResponseEntity.ok().build();
    }

    private void migration(String sessionId) throws SQLException, InterruptedException {

        Connection connection = DriverManager.getConnection(
                MigrationData.urlTo,
                MigrationData.usernameTo,
                MigrationData.passwordTo
        );

        Statement statement = connection.createStatement();
        Thread.sleep(600);
        for (String s : GeneratedCreateSQLs.getCreateSQLs()) {
            System.out.println(s);
            statement.addBatch(s);
        }
        statement.executeBatch();
        statement.clearBatch();
        messagingTemplate.convertAndSend("/message/" + sessionId, "Skeleton created");
        Thread.sleep(600);
        for (String s : GeneratedPrimarySQLs.getPrimarySQLs()) {
            System.out.println(s);
            statement.addBatch(s);
        }
        statement.executeBatch();
        statement.clearBatch();
        messagingTemplate.convertAndSend("/message/" + sessionId, "Primary keys set");
        Thread.sleep(600);

        for (String s : GeneratedInsertSQLs.getGeneratedInsertSQLs()) {
            System.out.println(s);
            statement.addBatch(s);
        }
        int[] rows = statement.executeBatch();
        statement.clearBatch();
        messagingTemplate.convertAndSend("/message/" + sessionId, rows.length + " rows inserted");
        messagingTemplate.convertAndSend("/message/" + sessionId, "Data inserted");
        Thread.sleep(600);

        for (String s : GeneratedForeignSQls.getForeignSQLs()) {
            System.out.println(s);
            statement.addBatch(s);
        }
        statement.executeBatch();
        statement.clearBatch();
        messagingTemplate.convertAndSend("/message/" + sessionId, "Foreign keys set");
        Thread.sleep(600);
        messagingTemplate.convertAndSend("/message/" + sessionId, "MIGRATION DONE SUCCESSFULLY");
        messagingTemplate.convertAndSend("/message/" + sessionId, "done");
    }

    private void preparation(String sessionId) throws SQLException, InterruptedException {
        List<String> strings = SchemaAnalyzer.generateSqls();
        for (String s : strings) {
            messagingTemplate.convertAndSend("/message/" + sessionId, "Warning: " + s);
            Thread.sleep(800);
        }
        messagingTemplate.convertAndSend("/message/" + sessionId, "done");
    }
}