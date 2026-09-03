package br.com.isiflix.appmoments.services;

import br.com.isiflix.appmoments.dto.LoginDTO;
import br.com.isiflix.appmoments.security.MomentsToken;

public interface IAuthService {
	public MomentsToken realizarLogin(LoginDTO dados);
}