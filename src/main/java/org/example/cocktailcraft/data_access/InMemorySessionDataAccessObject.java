package org.example.cocktailcraft.data_access;

import org.example.cocktailcraft.use_case.session.SessionDataAccessInterface;

public class InMemorySessionDataAccessObject
        implements SessionDataAccessInterface {

    private String currentUserId;

    @Override
    public void setCurrentUserId(String userId) {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException(
                    "User ID cannot be empty.");
        }

        currentUserId = userId;
    }

    @Override
    public String getCurrentUserId() {
        return currentUserId;
    }

    @Override
    public void clearSession() {
        currentUserId = null;
    }

    @Override
    public boolean isLoggedIn() {
        return currentUserId != null;
    }
}
