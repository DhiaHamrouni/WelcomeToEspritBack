package com.example.welcometoesprit.repository;

import com.example.welcometoesprit.entities.Complaint;
import com.example.welcometoesprit.entities.StatusComplaint;
import com.example.welcometoesprit.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplaintRepository extends BaseRepository<Complaint,Integer> {
    List<Complaint> findAllByStatusOrderByDate(StatusComplaint status);
   // List<Complaint> findAllByDateOrderByIdComplaint(Date date);
   // Complaint findByIdComplaint(Integer id);
    List<Complaint> findByComplaintPar(User user);
//       @Query(value = "select * from  Complaint e where e.Content = :n or e.Subject = :n ", nativeQuery = true)
//       List<Complaint> retrieveComplaintByTerm(@Param("n") String term);

    // to be modified to include user_name





}
