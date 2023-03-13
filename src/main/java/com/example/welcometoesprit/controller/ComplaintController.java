package com.example.welcometoesprit.controller;

import com.example.welcometoesprit.ServicesImpl.ComplaintServiceImp;
import com.example.welcometoesprit.ServicesImpl.MailingServiceImp;
import com.example.welcometoesprit.entities.*;
import com.example.welcometoesprit.repository.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/complaint")
@CrossOrigin("*")
public class ComplaintController extends BaseController<Complaint,Integer>{

    @Autowired
    ComplaintServiceImp complaintServiceImp;
    @Autowired

    MailingServiceImp mailingServiceImp;




    @GetMapping("/showComplaintsByStatus/{status}")
    public List<Complaint> showByStatus( @PathVariable("status") StatusComplaint statusComplaint) throws Exception {

        return complaintServiceImp.getComplaintsByStatus(statusComplaint);

    }

    @GetMapping("/showComplaintsByUser/{userId}")
    public List<Complaint> showByUser( @PathVariable("userId") Integer userId) throws Exception {

        return complaintServiceImp.getComplaintsByUser(userId);

    }



    // User related ( update , delete within 3 hours only )

    @PostMapping("/addComplaint/{userId}")
    public ResponseEntity<Complaint> addComplaint(@RequestBody Complaint e, @PathVariable("userId") Integer id) throws Exception {
        complaintServiceImp.AddComplaint(e, id);
        return ResponseEntity.ok(e);

    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Complaint> updateComplaint(@RequestBody Complaint e, @PathVariable("id") Integer id) throws Exception {
        complaintServiceImp.updateComplaint(e,id);
        return ResponseEntity.ok(e);


    }

    @DeleteMapping("/deleteComplaint/{id}")
    public ResponseEntity<?>  delete(@PathVariable("id") Integer id) throws Exception {

        this.complaintServiceImp.deletedComplaint3hours(id);

        return ResponseEntity.ok("Complaint deleted !");
    }



    @PutMapping("/Reject/{idComplaint}")

    public ResponseEntity<?> RejectComplaint(@RequestBody Mailingcontent mailingcontent, @PathVariable("idComplaint") Integer id){

        User user = complaintServiceImp.dismissComplaint(id);
        mailingServiceImp.sendEmail(user.getEmail(),
                mailingcontent.getSubject(),
                mailingcontent.getBody());


        return ResponseEntity.ok("email has been sent !");
    }

    @PutMapping("/Resolve/{idComplaint}")

    public ResponseEntity<?> ResolveComplaint(@RequestBody Mailingcontent mailingcontent, @PathVariable("idComplaint") Integer id){

        User user = complaintServiceImp.ResolveComplaint(id);
        mailingServiceImp.sendEmail(user.getEmail(),
                mailingcontent.getSubject(),
                mailingcontent.getBody());


        return ResponseEntity.ok("Complaint resolved , email has been sent ");
    }



    // Disciplinary Action
    @PutMapping("/Action/{idComplaint}/{idUser}/{Action}")

    public ResponseEntity<?> Action(@PathVariable("idComplaint") Integer id,@PathVariable("idUser") Integer idUser,@PathVariable("Action") String action){

        Mailingcontent mailingcontent = complaintServiceImp.takeDisciplinaryAction(id,idUser,action);
        mailingServiceImp.sendEmail(mailingcontent.getToEmail(),
                mailingcontent.getSubject(),
                mailingcontent.getBody());


        return ResponseEntity.ok("email has been sent !");
    }


    @PutMapping("/updateStatus/{id}/{status}")
    public ResponseEntity<Complaint> updateComplaintStatus(@PathVariable("id") Integer id, @PathVariable("status") StatusComplaint e) throws Exception {

        return ResponseEntity.ok(this.complaintServiceImp.UpdateStatusComplaint(e, id));


    }



}
