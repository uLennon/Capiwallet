package com.digital.wallet.repositories;

import com.digital.wallet.model.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompraRepository extends JpaRepository<Compra,Long> {
    @Query("SELECT c FROM Compra c WHERE c.usuario.id = :usuarioId ORDER BY c.id DESC")
    Optional<List<Compra>> findCompraPendenteByUsuarioId(@Param("usuarioId") Long usuarioId);

    @Query("SELECT c FROM Compra c WHERE c.usuario.id = :usuarioId AND c.status = 'CONCLUIDA'")
    List<Compra> listaComprasFeita(@Param("usuarioId") Long usuarioId);
}
