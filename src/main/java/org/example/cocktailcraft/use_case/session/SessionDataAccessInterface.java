package org.example.cocktailcraft.use_case.session;

public interface SessionDataAccessInterface {
    void setCurrentUserId(String userId);

    String getCurrentUserId();

    void clearSession();

    boolean isLoggedIn();
}
