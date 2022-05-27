package com.phone.book.repo;

import javax.transaction.Transactional;

import com.phone.book.entity.OtpDetails;
import com.phone.book.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpRepo extends JpaRepository<OtpDetails, Integer> {
    
    @Modifying
    @Transactional
    @Query(value = "update otp_details set otp = ?2 where user_id = ?1 ", nativeQuery = true )
    void updateOtp (int user , int otp) ;

    OtpDetails findByUser(User user);
}
