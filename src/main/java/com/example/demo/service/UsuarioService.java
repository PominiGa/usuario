package com.example.demo.service;

import com.example.demo.dto.UsuarioDTO;
import com.example.demo.dto.converter.UsuarioConverter;
import com.example.demo.infrastructure.entity.Usuario;
import com.example.demo.infrastructure.exceptions.ConflictException;
import com.example.demo.infrastructure.repository.UsuarioRepository;
import com.example.demo.infrastructure.security.SecurityConfig;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final SecurityConfig securityConfig;
    private final UsuarioConverter usuarioConverter;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          SecurityConfig securityConfig,
                          UsuarioConverter usuarioConverter) {
        this.usuarioRepository = usuarioRepository;
        this.securityConfig = securityConfig;
        this.usuarioConverter = usuarioConverter;
    }

    public UsuarioDTO criarUsuario(UsuarioDTO usuarioDTO) {
        checarEmail(usuarioDTO.getEmail());

        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        usuario.setSenha(securityConfig.passwordEncoder().encode(usuarioDTO.getSenha()));

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return usuarioConverter.paraUsuarioDTO(usuarioSalvo);
    }

    private void checarEmail(String email) {
        boolean existe = usuarioRepository.existsByEmail(email);
        if (existe) {
            throw new ConflictException("Email ja cadastrado: " + email);
        }
        if (email.isEmpty() || email.equals(null)) {
            throw new ConflictException("Email não pode estar em branco: " + email);
        }
        if (!email.contains("@") || !email.contains(".")) {
            throw new ConflictException("Email não permitido: " + email);
        }
    }

    public List<UsuarioDTO> listarTodos() {
        return usuarioRepository.findAll().stream().map(usuarioConverter::paraUsuarioDTO).toList();
    }
}
