package com.BERMUDEZCARLOS.BermudezCarlos.controller;

import com.BERMUDEZCARLOS.BermudezCarlos.dto.TurnoDTO;
import com.BERMUDEZCARLOS.BermudezCarlos.entity.Odontologo;
import com.BERMUDEZCARLOS.BermudezCarlos.entity.Paciente;
import com.BERMUDEZCARLOS.BermudezCarlos.exceptions.BadRequestException;
import com.BERMUDEZCARLOS.BermudezCarlos.exceptions.ResourceNotFoundException;
import com.BERMUDEZCARLOS.BermudezCarlos.service.OdontologoService;
import com.BERMUDEZCARLOS.BermudezCarlos.service.PacienteService;
import com.BERMUDEZCARLOS.BermudezCarlos.service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turnos")
public class TurnoController {

    private TurnoService turnoService;
    private PacienteService pacienteService;
    private OdontologoService odontologoService;
    @Autowired
    public TurnoController(TurnoService turnoService, PacienteService pacienteService, OdontologoService odontologoService) {
        this.turnoService = turnoService;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
    }

    @GetMapping
    public ResponseEntity<List<TurnoDTO>> buscarTurnos(){
        return ResponseEntity.ok(turnoService.listarTurnos());
    }
    @PostMapping
    public ResponseEntity<TurnoDTO> registrarTurno(@RequestBody TurnoDTO turno) throws BadRequestException {
        //si el odontologo o paciente no existe error
        //OdontologoService odontologoService=new OdontologoService();
        //PacienteService pacienteService=new PacienteService();
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarOdontologo(turno.getOdontologoId());
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPaciente(turno.getPacienteId());

        if (odontologoBuscado.isPresent() && pacienteBuscado.isPresent()){
            //ambos existen, puedo guardar el turno
            return ResponseEntity.ok(turnoService.guardarTurno(turno));
        }
        else {
            throw new BadRequestException("No se puede crear el turno porque no existe el paciente o el odontologo" +
                    "en la base de datos");
            //return ResponseEntity.badRequest().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarTurno(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<TurnoDTO> turnoBuscado= turnoService.buscarTurno(id);
        if (turnoBuscado.isPresent()){
            turnoService.eliminarTurno(id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Se eliminó el turno con id= " + id);
        }
        else {
            throw new ResourceNotFoundException("No se puede eliminar el turno con id= " + id);
            //return ResponseEntity.badRequest().body("No se puede eliminar el turno con id= " + id);
        }

    }
    @PutMapping
    public ResponseEntity<String> actualizarTurno(@RequestBody TurnoDTO turno){
        /*
        Podemos encontrar un problema con el id del turno
        Podemos encontrar un problema con el id del odontologo y/o paciente
         */

        Optional<TurnoDTO> turnoBuscado= turnoService.buscarTurno(turno.getId());
        if (turnoBuscado.isPresent()){
            //el turno existe, podemos verificar el resto
            //OdontologoService odontologoService=new OdontologoService();
            //PacienteService pacienteService=new PacienteService();
            if (odontologoService.buscarOdontologo(turno.getOdontologoId()).isPresent() && pacienteService.buscarPaciente(turno.getPacienteId()).isPresent()){
                //ambos existen, puedo guardar el turno
                turnoService.actualizarTurno(turno);
                return ResponseEntity.ok("Se actualizó el turno con id= " + turno.getId());
            }
            else {
                return ResponseEntity.badRequest().body("No se puede actualizar el uturno con" +
                        "id= " + turno.getId() + " debido que existe un error con el odontologo y/o paciente");
            }
        }
        else {
            return ResponseEntity.badRequest().body("No encontramos el turno que se quiere modificar");
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<TurnoDTO> buscarTurno(@PathVariable Long id){
        Optional<TurnoDTO> turnoBuscado= turnoService.buscarTurno(id);
        if (turnoBuscado.isPresent()){
            return ResponseEntity.ok(turnoBuscado.get());
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }


}
