package com.BERMUDEZCARLOS.BermudezCarlos.security;

import com.BERMUDEZCARLOS.BermudezCarlos.entity.Usuario;
import com.BERMUDEZCARLOS.BermudezCarlos.entity.UsuarioRole;
import com.BERMUDEZCARLOS.BermudezCarlos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatosInicialesUsuarios implements ApplicationRunner {
    @Autowired
    UsuarioRepository usuarioRepository;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        //crear un usario como si fuera real
        //guardarlo en la base
        BCryptPasswordEncoder cifrador= new BCryptPasswordEncoder();
        String passSinCifrar="digital";
        String passCifrada=cifrador.encode(passSinCifrar);
        Usuario usuarioAInsertar= new Usuario("Carlos","Carlos","carlosbermudez@gmail.com",passCifrada, UsuarioRole.ROLE_USER);
        usuarioRepository.save(usuarioAInsertar);
        //crear un usuario tipo admin
        String passCifrada2=cifrador.encode("house");
        usuarioAInsertar= new Usuario("Carlos","Carlos","carlosrios@gmail.com",passCifrada2 ,UsuarioRole.ROLE_ADMIN);
        usuarioRepository.save(usuarioAInsertar);
    }
}
