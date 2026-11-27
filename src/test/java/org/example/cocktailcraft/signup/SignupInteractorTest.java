package org.example.cocktailcraft.signup;

import org.example.cocktailcraft.data_access.CocktailJsonReader;
import org.example.cocktailcraft.data_access.InMemoryUserDataAccessObject;
import org.example.cocktailcraft.use_case.signup.SignupInputData;
import org.example.cocktailcraft.use_case.signup.SignupInteractor;
import org.example.cocktailcraft.use_case.signup.SignupOutputBoundary;
import org.example.cocktailcraft.use_case.signup.SignupOutputData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class SignupInteractorTest {

    @Test
    void successTest() {
        InMemoryUserDataAccessObject userDAO =
                new InMemoryUserDataAccessObject(new CocktailJsonReader());

        SignupInputData inputData =
                new SignupInputData("alice", "password123", "password123");

        SignupOutputBoundary presenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData outputData) {
                assertEquals("alice", outputData.getUsername());
                assertTrue(userDAO.existsByName("alice"));
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Signup should succeed: " + errorMessage);
            }
        };

        SignupInteractor interactor = new SignupInteractor(userDAO, presenter);

        interactor.execute(inputData);

        assertTrue(userDAO.existsByName("alice"));
    }
}

