package com.sunny.project.controllers;

import com.sunny.project.business.abstracts.UserService;

import com.sunny.project.dataAccess.UserRepo;
import com.sunny.project.entities.TaskPackage;
import com.sunny.project.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    @Autowired
    private UserRepo userRepo;

    private UserService userService;

    @GetMapping
    public List<User> getAll() {
        return this.userService.getAll();
    }
    @PostMapping
    public User createUser(@RequestBody User newUser) {
        return userService.save(newUser);
    }
    @GetMapping("/{userId}")
    public User getUserById(@PathVariable Long userId)
    {
        return userService.getUserById(userId).orElse(null);
    }

    @PutMapping("/{userId}")
    public User updateOneUser(@PathVariable Long userId, @RequestBody User newUser) {
        Optional<User> user = userService.findById(userId);
        if (user.isPresent()) {
            User foundUser = user.get();
            foundUser.setFirstName(newUser.getFirstName());
            foundUser.setLastName(newUser.getLastName());
            foundUser.setEmail(newUser.getEmail());
            foundUser.setProfession(newUser.getProfession());
            foundUser.setIsActive(newUser.getIsActive());
            foundUser.setIsAdmin(newUser.getIsAdmin());
            foundUser.setResignDate(newUser.getResignDate());
            userService.save(foundUser);
            return foundUser;
        } else return null;
    }
    @DeleteMapping("/{userId}")
    public void deleteOneUser(@PathVariable Long userId) {
        userService.deleteById(userId);
    }

    @GetMapping("/{userId}/taskPackages")
    public List<TaskPackage> getTaskPackagesByUser(@PathVariable Long userId) {
        return userService.getTaskPackagesByUserId(userId);
    }

}
