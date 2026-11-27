package org.example.cocktailcraft.use_case.login;

import org.example.cocktailcraft.entity.User;

public interface LoginDataAccessInterface {

    boolean existsByName(String username);

    boolean validateCredentials(String username, String password);

    User getUserByName(String username);
}

