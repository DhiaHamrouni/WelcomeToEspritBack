package com.example.welcometoesprit.ServicesImpl;


import com.example.welcometoesprit.ServiceInterface.ComplaintServiceInterface;
import com.example.welcometoesprit.entities.Complaint;
import com.example.welcometoesprit.repository.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComplaintServiceImp extends BaseServiceImp<Complaint,Integer> implements ComplaintServiceInterface {

    @Autowired
    ComplaintRepository complaintRepository;
}
