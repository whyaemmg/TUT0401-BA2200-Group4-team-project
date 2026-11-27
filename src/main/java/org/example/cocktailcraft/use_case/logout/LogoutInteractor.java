package org.example.cocktailcraft.use_case.logout;

import org.example.cocktailcraft.use_case.session.SessionDataAccessInterface;

public class LogoutInteractor implements LogoutInputBoundary {

    private final SessionDataAccessInterface sessionDataAccessObject;
    private final LogoutOutputBoundary logoutPresenter;

    public LogoutInteractor(
            SessionDataAccessInterface sessionDataAccessObject,
            LogoutOutputBoundary logoutPresenter) {
        this.sessionDataAccessObject = sessionDataAccessObject;
        this.logoutPresenter = logoutPresenter;
    }

    @Override
    public void execute() {
        sessionDataAccessObject.clearSession();
        logoutPresenter.prepareSuccessView();
    }
}
