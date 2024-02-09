package com.api.web.service;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

import com.api.web.model.Persona;

@Service
public class ConsumoMicroservice {

	    private final String BASE_URL = "http://localhost:9091/usuarios/lista";
	    private final String BASE_URL_update = "http://localhost:9091/usuarios/editar";
	    private final String BASE_URL_Delete = "http://localhost:9091/usuarios";
	    private final String BASE_URL_Create = "http://localhost:9091/usuarios/agregar";

	    private final RestTemplate restTemplate;

	    @Autowired
	    public ConsumoMicroservice(RestTemplate restTemplate) {
	        this.restTemplate = restTemplate;
	    }

	    private HttpHeaders getHeadersWithAuthorization() {
	        String username = "admin";
	        String password = "123";
	        String credentials = username + ":" + password;
	        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

	        HttpHeaders headers = new HttpHeaders();
	        headers.set("Authorization", "Basic " + encodedCredentials);

	        return headers;
	    }

	    public List<Persona> obtenerListaDePersonas() {
	        String url = BASE_URL;

	        // Configurar la solicitud con el encabezado
	        HttpEntity<String> requestEntity = new HttpEntity<>(getHeadersWithAuthorization());

	        // Realizar la solicitud GET al microservicio y obtener el resultado como un array de Personas
	        ResponseEntity<Persona[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Persona[].class);

	        return Arrays.asList(responseEntity.getBody());
	    }

	    public Persona agregarPersona(Persona persona) {
	        String url = BASE_URL_Create;

	        // Configurar la solicitud con el encabezado
	        HttpEntity<Persona> requestEntity = new HttpEntity<>(persona, getHeadersWithAuthorization());

	        return restTemplate.postForObject(url, requestEntity, Persona.class);
	    }

	    public boolean eliminarPersona(Long id) {
	        String url = BASE_URL_Delete + "/eliminar/{id";

	        // Configurar la solicitud con el encabezado
	        HttpEntity<String> requestEntity = new HttpEntity<>(getHeadersWithAuthorization());

	        restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Void.class, id);

	        return true;
	    }

	    public ResponseEntity<Persona> editarPersona(Long id, Persona persona) {
	        String url = BASE_URL_update + id;

	        // Configurar la solicitud con el encabezado
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        headers.addAll(getHeadersWithAuthorization());

	        HttpEntity<Persona> requestEntity = new HttpEntity<>(persona, headers);

	        return restTemplate.exchange(url, HttpMethod.PATCH, requestEntity, Persona.class);
	    }
	}
