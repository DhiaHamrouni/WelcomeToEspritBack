package com.example.welcometoesprit.ServicesImpl;


import com.example.welcometoesprit.ServiceInterface.ComplaintServiceInterface;
import com.example.welcometoesprit.entities.*;
import com.example.welcometoesprit.repository.ComplaintRepository;
import com.example.welcometoesprit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ComplaintServiceImp extends BaseServiceImp<Complaint,Integer> implements ComplaintServiceInterface {

    @Autowired
    ComplaintRepository complaintRepository;
    @Autowired

    UserRepository userRepository;


    // User related

    // Add complaint and assign to user
    @Override
    public Complaint AddComplaint(Complaint e, Integer id) throws Exception {
        User user = userRepository.getReferenceById(id);
        List<Complaint> complaints =getComplaintsByUser(id);
        complaints.add(e);
        user.setListOfComplaints(complaints);
        userRepository.save(user);
        return e;


    }


    // update within 3 hours

        @Override
    public Complaint updateComplaint(Complaint e, Integer id) throws Exception {


        LocalDateTime UpdateDate = LocalDateTime.now();
        Complaint existingComplaint = complaintRepository.findById(id).orElse(null);
        if (existingComplaint == null) {
            return null;
        }

        LocalDateTime currentTime = LocalDateTime.now();
        long hoursSinceCreation = ChronoUnit.HOURS.between(existingComplaint.getDate(), currentTime);
        if (hoursSinceCreation > 3) {
            throw new Exception("Complaint can no longer be updated");
        }

        existingComplaint.setSubject(e.getSubject());
        existingComplaint.setContent(e.getContent());
        existingComplaint.setUpdatedate(LocalDateTime.from(UpdateDate));
        return complaintRepository.save(existingComplaint);
    }


    // delete within 3 hours
    @Override
    public void deletedComplaint3hours(Integer id) throws Exception {

        Complaint existingComplaint = complaintRepository.findById(id).orElse(null);
        if (existingComplaint == null) {
            throw new Exception("Complaint with id : " + id + " doesn't exist ");
        }
        LocalDateTime currentTime = LocalDateTime.now();
        long hoursSinceCreation = ChronoUnit.HOURS.between(existingComplaint.getDate(), currentTime);
        if (hoursSinceCreation > 3) {
            throw new Exception("Complaint can no longer be deleted");
        }

        this.complaintRepository.deleteById(id);

    }

    @Override
    public List<Complaint> getComplaintsByUser(Integer userID) {
        User user = userRepository.getReferenceById(userID);
        return complaintRepository.findByComplaintPar(user);
    }


    // Admin related

    @Override
    public List<Complaint> getComplaintsByStatus(StatusComplaint status) {
        return complaintRepository.findAllByStatusOrderByDate(status);

    }

    @Override
    public Complaint UpdateStatusComplaint(StatusComplaint status, Integer id) throws Exception {

        Complaint existingComplaint = complaintRepository.findById(id).orElse(null);
        assert existingComplaint != null;
        existingComplaint.setStatus(status);
        return complaintRepository.save(existingComplaint);

    }

                       // Actions //

    //    offer user explanation
    //    status : rejected

    @Override
    public User dismissComplaint(Integer id) {

        Complaint existingComplaint = complaintRepository.findById(id).orElse(null);
        assert existingComplaint != null;
        existingComplaint.setStatus(StatusComplaint.REJECTED);
        complaintRepository.save(existingComplaint);
        return existingComplaint.getComplaintPar();

    }

    @Override
    public User ResolveComplaint(Integer id) {

        Complaint existingComplaint = complaintRepository.findById(id).orElse(null);
        assert existingComplaint != null;
        existingComplaint.setStatus(StatusComplaint.RESOLVED);
        complaintRepository.save(existingComplaint);
        return existingComplaint.getComplaintPar();

    }


    @Override
    public Mailingcontent takeDisciplinaryAction(Integer complaintId, Integer userId, String action) {
        User user = userRepository.getReferenceById(userId);
        Mailingcontent mailingcontent = new Mailingcontent();
        mailingcontent.setToEmail(user.getEmail());

        // set complaint as Resolved
        Complaint existingComplaint = complaintRepository.findById(complaintId).orElse(null);
        assert existingComplaint != null;
        existingComplaint.setStatus(StatusComplaint.RESOLVED);
        complaintRepository.save(existingComplaint);

        // check action

        if (action.equalsIgnoreCase("warning"))
        {
            if (user.getWarnings()< 3)
            {
                user.setWarnings(user.getWarnings()+1);
                mailingcontent.setSubject("Warning");
                mailingcontent.setBody("Dear " + user.getFirstname() +" "+ user.getLastname() +",\n" +
                        "\n" +
                        "We'd like to inform you that we have received a complaint about your behavior in " +existingComplaint.getDate()
                        );
            }
            else
            {
                user.setStatus(Status.Suspended);
                mailingcontent.setSubject("Suspension");
                mailingcontent.setBody("Dear " + user.getFirstname() +" "+ user.getLastname() +",\n" +
                        "\n" +
                        "We'd like to inform you that, after careful consideration, we have decided to suspend you for 15 days.   \n" +
                        "Sincerely,"+
                        "\n" +
                        "Esprit" );

            }

        }
        else if (action.equalsIgnoreCase("expulsion"))
        {
            user.setStatus(Status.Expelled);
            mailingcontent.setSubject("Expulsion");
            mailingcontent.setBody("Dear " + user.getFirstname() +" "+ user.getLastname() +",\n"+
                    "We'd like to inform you that, after careful consideration, we have decided to expel you from Esprit .\n" +
                    "Sincerely,"+ "\n" +
                    "Esprit"
                    );
        }
        else if (action.equalsIgnoreCase("suspended"))
        {
            user.setStatus(Status.Suspended);
            mailingcontent.setSubject("Suspension");
            mailingcontent.setBody("Dear " + user.getFirstname() +" "+ user.getLastname() +",\n" +
                    "\n" +
                    "We'd like to inform you that, after careful consideration, we have decided to suspend you for 3 days.   \n" +
                    "Sincerely,"+
                    "\n" +
                    "Esprit" );
        }

        else {
            throw new RuntimeException("Action not defined");
        }

        return mailingcontent;
    }


}
