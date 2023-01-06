package com.BERMUDEZCARLOS.BermudezCarlos.service;

import com.BERMUDEZCARLOS.BermudezCarlos.entity.Usuario;
import com.BERMUDEZCARLOS.BermudezCarlos.repository.UsuarioRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {
    private static final Logger LOGGER = Logger.getLogger(UsuarioService.class);
    private UsuarioRepository usuarioRepository;
    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.info("Inicio del proceso de la validación de los datos del usuario");
        Optional<Usuario> usuarioBuscadd=usuarioRepository.findByEmail(username);
        if (usuarioBuscadd.isPresent()){
            return usuarioBuscadd.get();
        }
        else {
            throw new UsernameNotFoundException("No existe el usuario con dicho correo en la base de datos");
        }
    }
}
