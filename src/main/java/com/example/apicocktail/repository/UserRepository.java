package com.example.apicocktail.repository;

import com.example.apicocktail.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    // Método para buscar todos los usuarios
    List<User> findAll();

    // Método para buscar un usuario por nombre de usuario
    List<User> findByUsername(String username);

    // Método para buscar un usuario por email
    List<User> findByEmail(String email);
}

