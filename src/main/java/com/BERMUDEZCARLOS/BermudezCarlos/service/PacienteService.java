package com.BERMUDEZCARLOS.BermudezCarlos.service;

import com.BERMUDEZCARLOS.BermudezCarlos.entity.Paciente;
import com.BERMUDEZCARLOS.BermudezCarlos.repository.PacienteRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class PacienteService {
    private PacienteRepository pacienteRepository;
    private static final Logger LOGGER= Logger.getLogger(PacienteService.class);

    @Autowired
    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public Paciente guardarPaciente(Paciente paciente) {
        LOGGER.info("Inicio el proceso de guardado del paciente con apellido: " + paciente.getApellido());
        return pacienteRepository.save(paciente);
    }

    public Optional<Paciente> buscarPaciente(Long id) {
        LOGGER.info("Inicio el proceso de buscar paciente con el id: " + id);
        return pacienteRepository.findById(id);
    }

    public void actualizarPaciente(Paciente paciente) {
        LOGGER.info("Inicio el proceso de actualizar el paciente con el id: " + paciente.getId());
        pacienteRepository.save(paciente);
    }

    public void eliminarPaciente(Long id){
        LOGGER.warn("Se eliminó el paciente con id: " + id);
        pacienteRepository.deleteById(id);
    }

    public List<Paciente> buscarTodosLosPacientes(){
        LOGGER.info("Inicio el proceso de búsqueda de los pacientes");
        return pacienteRepository.findAll();
    }

    public Optional<Paciente> buscarPacientePorCorreo(String correo){
        LOGGER.info("Inicio el proceso de búsqueda del paciente con correo: " + correo);
        return pacienteRepository.findByEmail(correo);
    }

}
