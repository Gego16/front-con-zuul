package com.api.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.web.model.Usuario;

public interface UsuarioDao extends JpaRepository<Usuario, Long>{

	Usuario findByUsername(String username);
}
