package com.BERMUDEZCARLOS.BermudezCarlos.service;


import com.BERMUDEZCARLOS.BermudezCarlos.entity.Odontologo;
import com.BERMUDEZCARLOS.BermudezCarlos.exceptions.ResourceNotFoundException;
import com.BERMUDEZCARLOS.BermudezCarlos.repository.OdontologoRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService {
    private static final Logger LOGGER= Logger.getLogger(OdontologoService.class);
    private OdontologoRepository odontologoRepository;

    @Autowired
    public OdontologoService(OdontologoRepository odontologoRepository) {
        this.odontologoRepository = odontologoRepository;
    }

    public Odontologo guardarOdontologo(Odontologo odontologo){
        LOGGER.info("Inicio del proceso de guardar el odontologo con apellido: " + odontologo.getApellido());
        return odontologoRepository.save(odontologo);
    }

    public Optional<Odontologo> buscarOdontologo(Long id){
        LOGGER.info("Se inició el proceso de búsqueda del odontologo con id: " + id);
        return odontologoRepository.findById(id);
    }

    public void actualizarOdontologo(Odontologo odontologo){
        LOGGER.info("Se inició el proceso de actualización del odontologo con id: " + odontologo.getId());
        odontologoRepository.save(odontologo);
    }

    public void eliminarOdontologo(Long id) throws ResourceNotFoundException {
        Optional<Odontologo> odontologoAEliminar=buscarOdontologo(id);
        if (odontologoAEliminar.isPresent()){
            odontologoRepository.deleteById(id);
            LOGGER.warn("Se eliminó el odontologo con id: " + id);
        }
        else {
            throw new ResourceNotFoundException(("No se puede eliminar el paciente " +
                    "con id= " + id + " porque no existe un odontologo en la base de datos" +
                    " con el mencionado id"));
        }
    }

    public List<Odontologo> buscarTodosOdontologos(){
        LOGGER.info("Se inició el proceso de búsqueda de los odontologos");
        return odontologoRepository.findAll();
    }

}
