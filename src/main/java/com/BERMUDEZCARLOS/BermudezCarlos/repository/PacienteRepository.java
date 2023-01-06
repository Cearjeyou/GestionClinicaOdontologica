package com.BERMUDEZCARLOS.BermudezCarlos.repository;

import com.BERMUDEZCARLOS.BermudezCarlos.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente,Long> {
    Optional<Paciente> findByEmail(String email);
    //forma de devolcer una lista con los resultados
    //List<Paciente> findByEmail(String email);
}
