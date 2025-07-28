package com.digital.wallet.repositories;

import com.digital.wallet.model.ReembolsoTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReembolsoTicketRepository extends JpaRepository<ReembolsoTicket, Long> {
}
