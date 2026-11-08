package com.example.demo.service;

import com.example.demo.infrastructure.entity.Usuario;
import com.example.demo.infrastructure.exceptions.ConflictException;
import com.example.demo.infrastructure.repository.UsuarioRepository;
import com.example.demo.infrastructure.security.SecurityConfig;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final SecurityConfig securityConfig;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          SecurityConfig securityConfig) {
        this.usuarioRepository = usuarioRepository;
        this.securityConfig = securityConfig;
    }

    public Usuario criarUsuario(Usuario usuario) {
        checarEmail(usuario.getEmail());
        usuario.setSenha(securityConfig.passwordEncoder().encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    private void checarEmail(String email) {
        boolean existe = usuarioRepository.existsByEmail(email);
        if (existe) {
            throw new ConflictException("Email ja cadastrado");
        }
        if (email.isEmpty() || email.equals(null)) {
            throw new ConflictException("Email não pode estar em branco");
        }
        if (!email.contains("@") || !email.contains(".")) {
            throw new ConflictException("Email não permitido");
        }
    }
}
