package com.digital.wallet.repositories;

import com.digital.wallet.model.CompraPresencial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CompraPresencialRepository extends JpaRepository<CompraPresencial, Long> {

 @Query(nativeQuery = true, value =
         "SELECT NULL as usuario_id, c.id, c.data, 'Pix' as nome_vendedor, c.quantidade_ticket_refeicao, c.quantidade_ticket_cafe_manha, c.valor, NULL as matricula " +
                 "FROM ru.compra c WHERE c.usuario_id = ?1 AND c.status = 'CONCLUIDA' " +
                 "UNION ALL " +
                 "SELECT NULL as usuario_id, cp.id, cp.data, 'Dinheiro' AS nome_vendedor, cp.quantidade_ticket_refeicao, cp.quantidade_ticket_cafe_manha, cp.valor, NULL as matricula " +
                 "FROM ru.compra_presencial cp WHERE cp.matricula = ?2"
 )
 List<CompraPresencial> comprasRealizadas(Long idUsuario,String matricula);
}
