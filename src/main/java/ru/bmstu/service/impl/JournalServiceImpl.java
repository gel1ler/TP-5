package ru.bmstu.service.impl;

import org.springframework.stereotype.Service;
import ru.bmstu.model.UserRole;
import ru.bmstu.service.JournalService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class JournalServiceImpl implements JournalService {
    @Override
    public void logAction(UserRole role, String action) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        System.out.printf("[%s] [%s] %s%n", timestamp, role, action);
    }
}
