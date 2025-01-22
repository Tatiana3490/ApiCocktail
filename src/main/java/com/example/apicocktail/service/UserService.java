package com.example.apicocktail.service;

import com.example.apicocktail.exception.UserNotFoundException;
import com.example.apicocktail.repository.UserRepository;
import com.example.apicocktail.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Para obtener todos los usuarios
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    //Para obtener el usuario por nombre de usuario
    public List<User> getUsersByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    //Para obtener el usuario por email
    public List<User> getUsersByEmail(String email) {
        return userRepository.findByEmail(email);
    }

   //Para guardar un nuevo usuario
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    // Actualizar un usuario por ID
    public User updateUser(Long id, User userDetails) throws UserNotFoundException {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        // Actualizar los campos necesarios
        existingUser.setUsername(userDetails.getUsername());
        existingUser.setSurname(userDetails.getSurname());
        existingUser.setPassword(userDetails.getPassword());

        return userRepository.save(existingUser);
    }

    public User updateUserPartial(Long id, Map<String, Object> updates) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(User.class, key);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, user, value);
            }
        });

        return userRepository.save(user);
    }


    // Eliminar un usuario por ID
    public void deleteUser(Long id) throws UserNotFoundException {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }






}
