package com.example.welcometoesprit.controller;

import com.example.welcometoesprit.ServicesImpl.UserServiceImp;
import com.example.welcometoesprit.entities.NiveauActuel;
import com.example.welcometoesprit.entities.NiveauSuivant;
import com.example.welcometoesprit.entities.ResponseMessage;
import com.example.welcometoesprit.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @PostMapping("addFileAndAssignToStudent/{idUser}")
    public ResponseEntity<ResponseMessage> addFileAndAssignToStudent(@RequestParam("file") MultipartFile file, @PathVariable Integer idUser){
        String message = "";
        System.out.println(""+file.getName()+"-"+file.getContentType());
        try {
            userService.addFileAndAssignToStudent(file,idUser);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "! Erreur :"+ e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }
    @PutMapping("/levelSuggestion/{idUser}")
    public NiveauSuivant levelSuggestion(@PathVariable Integer idUser){
        return userService.levelSuggestion(idUser);
    }

    @PutMapping("/refuseLevelSuggestion/{idUser}")
    public void refuseNextLevel(@PathVariable Integer idUser,@RequestBody User user){
        userService.refuseNextLevel(idUser,user);
    }
    @PutMapping("/addActualLevel/{idUser}")
    public NiveauActuel addActualLevel(@PathVariable Integer idUser, @RequestBody User user){
        return userService.addNiveauActuel(idUser,user);
    }

    @GetMapping("/countStudentsBylevel")
    public ResponseEntity<Map<String, Integer>> getStudentsCountByLevel() {
        Map<String, Integer> levelCountMap = userService.getStudentsCountByLevel();
        return ResponseEntity.ok(levelCountMap);
    }








}
