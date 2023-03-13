package com.example.welcometoesprit.controller;

import com.example.welcometoesprit.ServicesImpl.PDFGeneratorService;
import com.example.welcometoesprit.ServicesImpl.UserServiceImp;

import com.example.welcometoesprit.dto.TeacherDto;
import com.example.welcometoesprit.dto.UserDTO;

import com.example.welcometoesprit.entities.*;
import com.example.welcometoesprit.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
@Slf4j
public class UserController extends BaseController<User,Integer>   {
    private final PDFGeneratorService pdfGeneratorService;

    @Autowired
    private UserServiceImp userService;

    private final UserRepository userRepository;

    public UserController(PDFGeneratorService pdfGeneratorService,
                          UserRepository userRepository) {
        this.pdfGeneratorService = pdfGeneratorService;
        this.userRepository = userRepository;
    }

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

    @GetMapping("/certificate/generate/{id_user}")
    @ResponseBody
    public void exportCertificate(HttpServletResponse response,@PathVariable("id_user")  Integer id_user) throws IOException {
        response.setContentType("application/pdf");
        User user =userRepository.getReferenceById(id_user);
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=certificattion_" + user.getFirstname()+"_"+user.getLastname() + ".pdf";
        response.setHeader(headerKey, headerValue);
        this.userService.exportCertificate(response, id_user);
    }

    @GetMapping("/badge/generate/{id_user}")
    @ResponseBody
    public void exportBadge(HttpServletResponse response,@PathVariable("id_user")  Integer id_user) throws IOException {
        response.setContentType("application/pdf");
        User user =userRepository.getReferenceById(id_user);
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=badge" + user.getFirstname()+"_"+user.getLastname() + ".pdf";
        response.setHeader(headerKey, headerValue);
        this.userService.badgePdf(response, id_user);
    }

    @GetMapping("/export-to-excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Users_Information.xlsx";
        response.setHeader(headerKey, headerValue);
        userService.exportUserToExcel(response);
    }
    @PostMapping("/assignEventToUser/{id_user}/{id_event}")
    public void assignEventToUser(@PathVariable("id_user") Integer id_user,@PathVariable("id_event") Integer id_event){
        userService.assignEventToUser(id_user,id_event);
    }
//    @GetMapping(value = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
//    public ResponseEntity<byte[]> generatePdf() {
//        try {
//            byte[] pdfBytes = UserServiceImp.PdfGenerator.generatePdf();
//            return ResponseEntity.ok(pdfBytes);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(500).build();
//        }
//    }

    @GetMapping(value = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generatePdf() {
        try {
            byte[] pdfBytes = UserServiceImp.PdfGenerator.generatePdf();
            return ResponseEntity.ok(pdfBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }


    @GetMapping("/statistique/{role}/{cretetria}")
    public String statistique(@PathVariable("role") String role,@PathVariable("cretetria") String cretetria) {
        return userService.statistique(role,cretetria);
    }

    @PostMapping("assignInterviewToStudent/{idStudent}")
    public String assignInterviewToStudent(@PathVariable Integer idStudent,@RequestBody Interview interview){
        Date dateInterview = interview.getDateInterview();
        Integer heureInterview = interview.getHeureInterview();
        return userService.addInterviewAndAssignToStudent(idStudent,dateInterview,heureInterview);
    }

    @PutMapping("/assignInterviewToTeacher/{idStudent}")
    public void assignInterviewToteacher(@PathVariable Integer idStudent){
         userService.assignInterviewToTeacher(idStudent);
    }


    @GetMapping("/getStudentsByFirstName")
    public List<UserDTO> getStudentsByFirstName(@RequestParam String firstName) {
        UserDTO userDto = new UserDTO();
        userDto.setFirstName(firstName);
        //log.info("le firstName de letudiant est "+userDto.getFirstName());
        return userService.findStudentsByFirstName(userDto);

    }

    @GetMapping("/getTeachersByFirstNameAndLastName")
    public ResponseEntity<List<TeacherDto>> getTeachersByFirstNameAndLastName(@RequestParam String firstName, @RequestParam String lastName) {
        List<TeacherDto> teacherDtoList = userService.findTeachersByFirstNameAndLastName(firstName, lastName);
        return ResponseEntity.ok(teacherDtoList);
    }








}
