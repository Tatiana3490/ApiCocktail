package com.example.apicocktail.controller;


import com.example.apicocktail.exception.UserNotFoundException;
import com.example.apicocktail.service.UserService;
import com.example.apicocktail.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/user")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //Para obtener todos los usuarios
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        logger.info("getAllUsers");
        List<User> users = userService.getAllUsers();
        logger.info("getAllUsers - Total user fetched: " + users.size());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    //Agregar un nuevo usuario
    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        logger.info("BEGIN addUser - Adding user: " + user);
        User newUser = userService.saveUser(user);
        logger.info("END addUser - Adding user: " + newUser);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    //Para buscar usuario por username
    @GetMapping
    public ResponseEntity<List<User>> getUserByUsername(@RequestParam String username) {
        logger.info("BEGIN getUserByUsername - username: " + username);
        List<User> users = userService.getUsersByUsername(username);
        logger.info("END getUserByUsername - Total users fetched: " + users.size());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    //Para buscar usuario por email
    @GetMapping("/email")
    public ResponseEntity<List<User>> getUserByEmail(@RequestParam String email) {
        logger.info("BEGIN getUserByEmail - Searching user by email: " + email);
        List<User> user = userService.getUsersByEmail(email);
        logger.info("END getUserByEmail - Total users found: {} ", user.size());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    // Actualizar un usuario por ID
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        logger.info("BEGIN updateUser - Updating user with ID: {}", id);
        try {
            User updatedUser = userService.updateUser(id, userDetails);
            logger.info("END updateUser - User updated with ID: {}", id);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            logger.error("Error in updateUser - User not found with ID: {}", id, e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Actualizar parcialmente un usuario por ID
    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        logger.info("BEGIN updateUserPartial - Partially updating user with ID: {}", id);
        User updatedUser = userService.updateUserPartial(id, updates);
        logger.info("END updateUserPartial - User updated with ID: {}", id);
        return ResponseEntity.ok(updatedUser);
    }

    // Eliminar un usuario por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        logger.info("BEGIN deleteUser - Deleting user with ID: {}", id);
        try {
            userService.deleteUser(id);
            logger.info("END deleteUser - User deleted with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (UserNotFoundException e) {
            logger.error("Error in deleteUser - User not found with ID: {}", id, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Manejar excepciones de usuario no encontrado
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException exception) {
        logger.error("Handling UserNotFoundException - {}", exception.getMessage(), exception);
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }







}