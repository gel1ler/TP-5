package ru.bmstu.service;

import ru.bmstu.model.UserRole;

public interface JournalService {
    void logAction(UserRole role, String action);
}