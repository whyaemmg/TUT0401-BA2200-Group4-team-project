package org.example.cocktailcraft.login;

import java.util.concurrent.atomic.AtomicBoolean;

import org.example.cocktailcraft.data_access.InMemorySessionDataAccessObject;
import org.example.cocktailcraft.data_access.InMemoryUserDataAccessObject;
import org.example.cocktailcraft.use_case.login.LoginInputData;
import org.example.cocktailcraft.use_case.login.LoginInteractor;
import org.example.cocktailcraft.use_case.login.LoginOutputBoundary;
import org.example.cocktailcraft.use_case.login.LoginOutputData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class LoginInteractorTest {

    @Test
    void successTest() {
        InMemoryUserDataAccessObject userDAO =
                new InMemoryUserDataAccessObject();

        InMemorySessionDataAccessObject sessionDAO =
                new InMemorySessionDataAccessObject();

        userDAO.createUser("alice", "password123");

        String expectedUserId =
                userDAO.getUserByName("alice").getId();

        LoginInputData inputData =
                new LoginInputData("alice", "password123");

        AtomicBoolean successCalled = new AtomicBoolean(false);

        LoginOutputBoundary presenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData outputData) {
                assertEquals("alice", outputData.getUsername());
                assertEquals(expectedUserId, sessionDAO.getCurrentUserId());
                successCalled.set(true);
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Login should succeed: " + errorMessage);
            }
        };

        LoginInteractor interactor =
                new LoginInteractor(userDAO, presenter, sessionDAO);

        interactor.execute(inputData);

        assertTrue(successCalled.get());
        assertTrue(sessionDAO.isLoggedIn());
        assertEquals(expectedUserId, sessionDAO.getCurrentUserId());
    }

    @Test
    void incorrectPasswordTest() {
        assertLoginFailure("alice", "wrongpassword",
                "Invalid username or password.");
    }

    @Test
    void unknownUserTest() {
        assertLoginFailure("unknown", "password123",
                "Invalid username or password.");
    }

    @Test
    void emptyUsernameTest() {
        assertLoginFailure("", "password123",
                "Username cannot be empty.");
    }

    @Test
    void emptyPasswordTest() {
        assertLoginFailure("alice", "",
                "Password cannot be empty.");
    }

    @Test
    void failedLoginPreservesExistingSessionTest() {
        InMemoryUserDataAccessObject userDAO =
                new InMemoryUserDataAccessObject();

        InMemorySessionDataAccessObject sessionDAO =
                new InMemorySessionDataAccessObject();

        userDAO.createUser("alice", "password123");
        userDAO.createUser("bob", "password456");

        String aliceId = userDAO.getUserByName("alice").getId();
        sessionDAO.setCurrentUserId(aliceId);

        AtomicBoolean failureCalled = new AtomicBoolean(false);

        LoginOutputBoundary presenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData outputData) {
                fail("Login should fail.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Invalid username or password.", errorMessage);
                failureCalled.set(true);
            }
        };

        LoginInteractor interactor =
                new LoginInteractor(userDAO, presenter, sessionDAO);

        interactor.execute(new LoginInputData("bob", "wrongpassword"));

        assertTrue(failureCalled.get());
        assertTrue(sessionDAO.isLoggedIn());
        assertEquals(aliceId, sessionDAO.getCurrentUserId());
    }

    @Test
    void switchUserTest() {
        InMemoryUserDataAccessObject userDAO =
                new InMemoryUserDataAccessObject();

        InMemorySessionDataAccessObject sessionDAO =
                new InMemorySessionDataAccessObject();

        userDAO.createUser("alice", "password123");
        userDAO.createUser("bob", "password456");

        String aliceId = userDAO.getUserByName("alice").getId();
        String bobId = userDAO.getUserByName("bob").getId();

        sessionDAO.setCurrentUserId(aliceId);

        AtomicBoolean successCalled = new AtomicBoolean(false);

        LoginOutputBoundary presenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData outputData) {
                assertEquals("bob", outputData.getUsername());
                successCalled.set(true);
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Login should succeed: " + errorMessage);
            }
        };

        LoginInteractor interactor =
                new LoginInteractor(userDAO, presenter, sessionDAO);

        interactor.execute(new LoginInputData("bob", "password456"));

        assertTrue(successCalled.get());
        assertTrue(sessionDAO.isLoggedIn());
        assertEquals(bobId, sessionDAO.getCurrentUserId());
    }

    private void assertLoginFailure(String username,
                                    String password,
                                    String expectedError) {
        InMemoryUserDataAccessObject userDAO =
                new InMemoryUserDataAccessObject();

        InMemorySessionDataAccessObject sessionDAO =
                new InMemorySessionDataAccessObject();

        userDAO.createUser("alice", "password123");

        LoginInputData inputData =
                new LoginInputData(username, password);

        AtomicBoolean failureCalled = new AtomicBoolean(false);

        LoginOutputBoundary presenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData outputData) {
                fail("Login should fail.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals(expectedError, errorMessage);
                failureCalled.set(true);
            }
        };

        LoginInteractor interactor =
                new LoginInteractor(userDAO, presenter, sessionDAO);

        interactor.execute(inputData);

        assertTrue(failureCalled.get());
        assertFalse(sessionDAO.isLoggedIn());
        assertNull(sessionDAO.getCurrentUserId());
    }
}

