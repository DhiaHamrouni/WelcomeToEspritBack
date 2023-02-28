package com.example.welcometoesprit.repository;

import com.example.welcometoesprit.entities.Complaint;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplaintRepository extends BaseRepository<Complaint,Integer> {
//    List<Complaint> findAllByStatusOrderByDate(StatusComplaint status);
   // List<Complaint> findAllByDateOrderByIdComplaint(Date date);
   // Complaint findByIdComplaint(Integer id);
   // List<Complaint> findByUser(Users user);
    //    @Query(value = "select * from  Complaint e where e.Content = :n or e.Subject = :n or e.user.name= :n", nativeQuery = true)
    //   List<Complaint> retrieveComplaintByTerm(@Param("n") String term);

    // to be modified to include user_name
}
