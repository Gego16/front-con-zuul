package com.api.web.service;

import java.util.ArrayList;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.GrantedAuthority;

import com.api.web.dao.UsuarioDao;
import com.api.web.model.Rol;
import com.api.web.model.Usuario;

import lombok.extern.slf4j.Slf4j;

@Service("userDetailsService")
@Slf4j
public class UsuarioService implements UserDetailsService {
	
	@Autowired
	private UsuarioDao usuarioDao;
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioDao.findByUsername(username);
		
		if (usuario == null) {
			throw new UsernameNotFoundException(username);
		}
		
	 ArrayList<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
	 
	 for(Rol rol:usuario.getRoles()) {
		 
		 roles.add(new SimpleGrantedAuthority(rol.getNombre()));
		 
	 }
	 
	 return new User(usuario.getUsername(),usuario.getPassword(),roles);
	}
	
}

