package com.api.web.model;

import lombok.Data;

@Data
public class AuthenticationRequests {
	private String email;
    private String password;
}
