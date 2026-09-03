package br.com.isiflix.appmoments.security;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import br.com.isiflix.appmoments.model.Cliente;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.lang.Collections;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

public class TokenUtil {

	private static final long SEGUNDO = 1000;
	private static final long MINUTO  = 60 * SEGUNDO;
	private static final long HORA    = 60 * MINUTO;
	private static final long DIA     = 24 * HORA;
	private static final long DURACAO = 7 * DIA;
	
	private static final String PREFIX = "Bearer ";
	private static final String EMISSOR = "IsiFLIX";
	private static final String SECRET = "01234567890123456789012345678901";

	public static MomentsToken encode(Cliente cliente) {
		try {
			Key key = Keys.hmacShaKeyFor(SECRET.getBytes());
			String jwtToken = Jwts.builder().subject(cliente.getNome()+";"+cliente.getId())
			.issuer(EMISSOR)
			.expiration(new Date(System.currentTimeMillis() + DURACAO))
			.signWith(key)
			.compact();
			
			MomentsToken tk = new MomentsToken(PREFIX+jwtToken);
			return tk;
		}
		catch (Exception ex) {
			
		}
		return null;
	}

	public static Authentication decode(HttpServletRequest request)  {
		try {
			String token = request.getHeader("Authorization");
			if (token != null) {
				token = token.replace(PREFIX, "");
				SecretKey secretKey = Keys.hmacShaKeyFor(SECRET.getBytes());
				JwtParser jwtParser = Jwts.parser().verifyWith(secretKey).build();
				
				Claims claims = (Claims)jwtParser.parse(token).getPayload();
				String emissor = claims.getIssuer();
				String subject = claims.getSubject();
				Date   validade = claims.getExpiration();
				
				if (emissor.equals(EMISSOR) && subject!= null && subject.length() > 0) {
					if (validade.after(new Date(System.currentTimeMillis()))) {
						return new UsernamePasswordAuthenticationToken(subject,null, Collections.emptyList());
					}
					else {
						return null;
					}
				}
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	
	public static Integer getClienteByToken(String token) {
		try {
			token = token.replace(PREFIX, "");
			SecretKey secretKey = Keys.hmacShaKeyFor(SECRET.getBytes());
			JwtParser jwtParser = Jwts.parser().verifyWith(secretKey).build();
			
			Claims claims = (Claims)jwtParser.parse(token).getPayload();
			String subject = claims.getSubject();
			Integer id = Integer.parseInt(subject.substring(subject.indexOf(";")+1));
			return id;
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}