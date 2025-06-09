package ru.bmstu.repository.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.bmstu.model.UserRole;
import ru.bmstu.repository.JournalService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class JournalServiceImpl implements JournalService {
    private final Resource journalResource;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public JournalServiceImpl(@Qualifier("journalResource") Resource journalResource) {
        this.journalResource = journalResource;
    }

    @Override
    public void logAction(String action) {
        try {
            String record = String.format("%s,%s%n",
                    LocalDateTime.now().format(formatter),
                    action);

            Files.write(Paths.get(journalResource.getURI()),
                    record.getBytes(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}