package org.example.cocktailcraft.logout;

import java.util.concurrent.atomic.AtomicBoolean;

import org.example.cocktailcraft.data_access.InMemorySessionDataAccessObject;
import org.example.cocktailcraft.data_access.InMemoryUserDataAccessObject;
import org.example.cocktailcraft.use_case.logout.LogoutInteractor;
import org.example.cocktailcraft.use_case.logout.LogoutOutputBoundary;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LogoutInteractorTest {

    @Test
    void successTest() {
        InMemorySessionDataAccessObject sessionDAO =
                new InMemorySessionDataAccessObject();

        sessionDAO.setCurrentUserId("user-001");

        AtomicBoolean successCalled = new AtomicBoolean(false);

        LogoutOutputBoundary presenter = () -> {
            assertFalse(sessionDAO.isLoggedIn());
            assertNull(sessionDAO.getCurrentUserId());
            successCalled.set(true);
        };

        LogoutInteractor interactor =
                new LogoutInteractor(sessionDAO, presenter);

        interactor.execute();

        assertTrue(successCalled.get());
        assertFalse(sessionDAO.isLoggedIn());
        assertNull(sessionDAO.getCurrentUserId());
    }

    @Test
    void logoutWithoutActiveSessionTest() {
        InMemorySessionDataAccessObject sessionDAO =
                new InMemorySessionDataAccessObject();

        AtomicBoolean successCalled = new AtomicBoolean(false);

        LogoutOutputBoundary presenter =
                () -> successCalled.set(true);

        LogoutInteractor interactor =
                new LogoutInteractor(sessionDAO, presenter);

        interactor.execute();

        assertTrue(successCalled.get());
        assertFalse(sessionDAO.isLoggedIn());
        assertNull(sessionDAO.getCurrentUserId());
    }

    @Test
    void logoutPreservesUserAccountTest() {
        InMemoryUserDataAccessObject userDAO =
                new InMemoryUserDataAccessObject();

        InMemorySessionDataAccessObject sessionDAO =
                new InMemorySessionDataAccessObject();

        userDAO.createUser("alice", "password123");

        String userId = userDAO.getUserByName("alice").getId();

        sessionDAO.setCurrentUserId(userId);

        AtomicBoolean successCalled = new AtomicBoolean(false);

        LogoutOutputBoundary presenter =
                () -> successCalled.set(true);

        LogoutInteractor interactor =
                new LogoutInteractor(sessionDAO, presenter);

        interactor.execute();

        assertTrue(successCalled.get());
        assertFalse(sessionDAO.isLoggedIn());
        assertNull(sessionDAO.getCurrentUserId());

        assertTrue(userDAO.existsByName("alice"));
        assertTrue(userDAO.validateCredentials("alice", "password123"));
        assertEquals(userId, userDAO.getUserByName("alice").getId());
    }
}
