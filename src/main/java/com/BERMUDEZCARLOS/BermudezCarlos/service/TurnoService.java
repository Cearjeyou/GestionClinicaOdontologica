package com.BERMUDEZCARLOS.BermudezCarlos.service;

import com.BERMUDEZCARLOS.BermudezCarlos.dto.TurnoDTO;
import com.BERMUDEZCARLOS.BermudezCarlos.entity.Odontologo;
import com.BERMUDEZCARLOS.BermudezCarlos.entity.Paciente;
import com.BERMUDEZCARLOS.BermudezCarlos.entity.Turno;
import com.BERMUDEZCARLOS.BermudezCarlos.repository.TurnoRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService {
    private static final Logger LOGGER = Logger.getLogger(TurnoService.class);
    private TurnoRepository turnoRepository;
    @Autowired
    public TurnoService(TurnoRepository turnoRepository) {
        this.turnoRepository = turnoRepository;
    }



    public List<TurnoDTO> listarTurnos(){
        LOGGER.info("Inicio el proceso de búsqueda de los turnos");
        List<Turno> turnosEncontrados= turnoRepository.findAll();
        //recorremos la lista para ir convirtiendo cada elemento
        List<TurnoDTO> respuesta= new ArrayList<>();
        for (Turno turno : turnosEncontrados) {
            respuesta.add(turnoATurnoDTO(turno));
        }
        return respuesta;
    }

    public Optional<TurnoDTO> buscarTurno(Long id){
        Optional<Turno> turnoBuscado=turnoRepository.findById(id);
        if (turnoBuscado.isPresent()){
            //existe el turno
            LOGGER.info("Inicio el proceso de búsqueda del turno con el id: " + id);
            return Optional.of(turnoATurnoDTO(turnoBuscado.get()));
        }
        else {
            LOGGER.info("No existe el turno con id:" + id);
            //no existe turno, es nulo
            return Optional.empty();
        }
    }

    public void eliminarTurno(Long id){
        LOGGER.warn("Se eliminó el turno con id: " + id);
        turnoRepository.deleteById(id);
    }

    public void actualizarTurno(TurnoDTO turnodto){
        LOGGER.info("Inicio el proceso de actualización del turno con id: " + turnodto.getId());
        turnoRepository.save(turnoDTOATurno(turnodto));
    }

    public TurnoDTO guardarTurno(TurnoDTO turnodto){
        LOGGER.info("Inicio el proceso de guardado del turno con fecha: " + turnodto.getFecha());
        Turno turnoGuardado= turnoRepository.save(turnoDTOATurno(turnodto));
        return turnoATurnoDTO(turnoGuardado);
    }
    private TurnoDTO turnoATurnoDTO(Turno turno){
        //convertir el turno a un turnoDTO
        TurnoDTO respueesta= new TurnoDTO();
        //cargar la información de turno a turno DTO
        respueesta.setId(turno.getId());
        respueesta.setFecha(turno.getFecha());
        respueesta.setOdontologoId(turno.getOdontologo().getId());
        respueesta.setPacienteId(turno.getPaciente().getId());
        return respueesta;
    }
    private Turno turnoDTOATurno(TurnoDTO turnodto){
        Turno respuesta= new Turno();
        //cargar la información de turno DTO al turno
        Odontologo odontologo= new Odontologo();
        Paciente paciente= new Paciente();
        odontologo.setId(turnodto.getOdontologoId());
        paciente.setId(turnodto.getPacienteId());
        respuesta.setFecha(turnodto.getFecha());
        respuesta.setId(turnodto.getId());
        //no debemos olvidarnos de agregar ambos objetos
        respuesta.setOdontologo(odontologo);
        respuesta.setPaciente(paciente);
        //salida
        return respuesta;
    }

}
