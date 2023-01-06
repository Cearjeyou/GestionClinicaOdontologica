package com.BERMUDEZCARLOS.BermudezCarlos.controller;


import com.BERMUDEZCARLOS.BermudezCarlos.entity.Paciente;
import com.BERMUDEZCARLOS.BermudezCarlos.exceptions.ResourceNotFoundException;
import com.BERMUDEZCARLOS.BermudezCarlos.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    private PacienteService pacienteService;
    @Autowired
    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPaciente(@PathVariable Long id){
        Optional<Paciente> pacienteBuscado= pacienteService.buscarPaciente(id);
        if (pacienteBuscado.isPresent()){
            return ResponseEntity.ok(pacienteBuscado.get());
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> listarPacientes(){
        return ResponseEntity.ok(pacienteService.buscarTodosLosPacientes());
    }

    @GetMapping("/buscar/correo/{email}")
    public ResponseEntity<Paciente> buscarPacientePorCorreo(@PathVariable String email){
        Optional<Paciente> pacienteBuscado= pacienteService.buscarPacientePorCorreo(email);
        if (pacienteBuscado.isPresent()){
            return ResponseEntity.ok(pacienteBuscado.get());
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Paciente> registrarPaciente(@RequestBody Paciente paciente){
        //chequeo para controlar que sea un correo sin usar
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPacientePorCorreo(paciente.getEmail());
        if (pacienteBuscado.isPresent()){
            //existe un paciente con ese email
            return ResponseEntity.badRequest().build();
        }
        else {
            return ResponseEntity.ok(pacienteService.guardarPaciente(paciente));
        }
    }
    @PutMapping
    public ResponseEntity<String> actualizarPaciente(@RequestBody Paciente paciente){
        Optional<Paciente> pacienteBuscado=pacienteService.buscarPaciente(paciente.getId());
        if (pacienteBuscado.isPresent()){
            pacienteService.actualizarPaciente(paciente);
            return ResponseEntity.ok("Se actualizó el paciente con id= " + paciente.getId());
        }
        else {
            return ResponseEntity.ok("No se pudo actualizar al paciente con id= " + paciente.getId() +
                    " ya que el mismo no existe en la base de datos");
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPaciente(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPaciente(id);
        if (pacienteBuscado.isPresent()){
            pacienteService.eliminarPaciente(id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Se eliminó el paciente con id: " + id);
        }
        else {
            throw new ResourceNotFoundException("No se puede eliminar porque no existe en la base de datos  el odontologo con id: " + id);
            //return ResponseEntity.badRequest().body("No se puede eliminar porque no existe en la base de datos  el odontologo con id: " + id);
        }
    }
}
