package org.example.cocktailcraft.use_case.login;

import org.example.cocktailcraft.entity.User;
import org.example.cocktailcraft.use_case.session.SessionDataAccessInterface;

public class LoginInteractor implements LoginInputBoundary {

    private final LoginDataAccessInterface userDataAccessObject;
    private final LoginOutputBoundary loginPresenter;
    private final SessionDataAccessInterface sessionDataAccessObject;

    public LoginInteractor(
            LoginDataAccessInterface userDataAccessObject,
            LoginOutputBoundary loginPresenter,
            SessionDataAccessInterface sessionDataAccessObject) {
        this.userDataAccessObject = userDataAccessObject;
        this.loginPresenter = loginPresenter;
        this.sessionDataAccessObject = sessionDataAccessObject;
    }

    @Override
    public void execute(LoginInputData loginInputData) {
        String username = loginInputData.getUsername();
        String password = loginInputData.getPassword();

        if (username == null || username.isBlank()) {
            loginPresenter.prepareFailView("Username cannot be empty.");
        } else if (password == null || password.isBlank()) {
            loginPresenter.prepareFailView("Password cannot be empty.");
        } else if (!userDataAccessObject.validateCredentials(username, password)) {
            loginPresenter.prepareFailView("Invalid username or password.");
        } else {
            User user = userDataAccessObject.getUserByName(username);

            if (user == null) {
                loginPresenter.prepareFailView("User account not found.");
            } else {
                sessionDataAccessObject.setCurrentUserId(user.getId());

                LoginOutputData outputData = new LoginOutputData(username);
                loginPresenter.prepareSuccessView(outputData);
            }
        }
    }
}

