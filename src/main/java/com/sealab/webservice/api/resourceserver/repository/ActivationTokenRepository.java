package com.sealab.webservice.api.resourceserver.repository;

import com.sealab.webservice.api.resourceserver.entity.Activationtoken;
import com.sealab.webservice.api.resourceserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivationTokenRepository extends JpaRepository<Activationtoken, Long> {

    @Query("SELECT u FROM Activationtoken u WHERE u.confirmationToken = ?1")
    public Activationtoken getByConfirmationToken(String confirmationToken);

}
