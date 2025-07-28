package com.digital.wallet.repositories;

import com.digital.wallet.model.RegistroTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistroRepository extends JpaRepository<RegistroTicket, Long> {
}
