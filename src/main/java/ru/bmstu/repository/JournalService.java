package ru.bmstu.repository;

import ru.bmstu.model.UserRole;

public interface JournalService {
    void logAction(String action);
}