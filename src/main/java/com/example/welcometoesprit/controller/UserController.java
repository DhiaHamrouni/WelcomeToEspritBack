package com.example.welcometoesprit.controller;

import com.example.welcometoesprit.ServicesImpl.UserServiceImp;
import com.example.welcometoesprit.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController extends BaseController<User,Integer>   {
    @Autowired
    private UserServiceImp userService;
    @PostMapping("/upload-users-data")
    public ResponseEntity<?> uploadUsersData(@RequestParam("file") MultipartFile file){
        this.userService.saveUsersToDatabase(file);
        return ResponseEntity
                .ok(Map.of("Message" , " Users data uploaded and saved to database successfully"));
    }
}
