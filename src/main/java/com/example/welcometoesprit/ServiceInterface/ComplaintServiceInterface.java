package com.example.welcometoesprit.ServiceInterface;

import com.example.welcometoesprit.entities.Complaint;
import com.example.welcometoesprit.entities.Mailingcontent;
import com.example.welcometoesprit.entities.StatusComplaint;
import com.example.welcometoesprit.entities.User;

import java.util.List;


public interface ComplaintServiceInterface extends BaseServiceInterface<Complaint,Integer>{


    // user related
    public Complaint updateComplaint (Complaint e, Integer id) throws Exception;
    public void deletedComplaint3hours(Integer id)  throws Exception;
    public List<Complaint> getComplaintsByUser(Integer userId);
    public Complaint AddComplaint(Complaint e, Integer id) throws Exception;


    // Admin related
    public List<Complaint> getComplaintsByStatus(StatusComplaint status);
    public User ResolveComplaint(Integer id);
    public Complaint UpdateStatusComplaint(StatusComplaint status , Integer id) throws Exception;
    /// decisions ///

    // note : all decisions are diffused with email notifying the user or the subject of the complaint ( in cases of abuse .. ) => add parameter : email

    public User dismissComplaint(Integer complaintId);
//    offer user explanation + move from active Complaints table
//    status : rejected

//    public void setMeetingAndInvestigate(Complaint complaint , User user, Date date  );
//    set meeting ( calendar )
//    status: investigating


    public Mailingcontent takeDisciplinaryAction(Integer complaintId, Integer userId , String action );
//    set action ( suspension _ warning _ Community service _ Expulsion _ legal action ..)
//    status : resolved


//    public void resolveComplaint(Complaint complaint, User user ) ;
//    provide info (  Apology ,Offer solution : refund, a makeup exam, or a change in course schedule..  )
//    status : resolved

//  once a complaint is ' investigating ' , the admin manually changes the status to resolved once completed

}
