package com.BERMUDEZCARLOS.BermudezCarlos.repository;

import com.BERMUDEZCARLOS.BermudezCarlos.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String correo);
}
