package com.segurity.springSecurity.repositories;

import com.segurity.springSecurity.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {

    //Buscar por nombre de usuario
    Optional<Usuario> findByUsername(String username);

    //Verificar si un usuario existe en la base de datos
    Boolean existsByUsername(String username);
}
