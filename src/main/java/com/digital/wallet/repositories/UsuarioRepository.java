package com.digital.wallet.repositories;

import com.digital.wallet.model.Usuario;
import com.digital.wallet.util.Identificador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findAllByMatricula(String matricula);
    List<Usuario> findByTipoUsuario(Identificador tipoUsuario);
    Optional<Usuario> findByMatricula(String matricula);

}
