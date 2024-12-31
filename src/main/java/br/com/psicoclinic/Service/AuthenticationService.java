package br.com.psicoclinic.Service;

import br.com.psicoclinic.Models.VO.AccountCredentialsVO;
import br.com.psicoclinic.Models.VO.TokenVO;
import br.com.psicoclinic.Repositories.UserRepository;
import br.com.psicoclinic.SecurityJWT.TokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, TokenProvider tokenProvider, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @SuppressWarnings("rawtypes")
    public ResponseEntity signin(AccountCredentialsVO credentialsVO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credentialsVO.getUsername(),
                            credentialsVO.getPassword()
                    )
            );

            var user = userRepository.findByUsername(credentialsVO.getUsername());
            if (user == null) {
                throw new UsernameNotFoundException("Username: " + credentialsVO.getUsername() + " Not Found.");
            }

            var tokenResponse = tokenProvider.createAccessToken(credentialsVO.getUsername(), user.getRoles());

            return ResponseEntity.ok(tokenResponse);

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username or password. Try again.");
        }
    }

    @SuppressWarnings("rawtypes")
    public ResponseEntity refreshToken(String username, String refreshToken) {
        var user = userRepository.findByUsername(username);

        var tokenResponse = new TokenVO();
        if (tokenResponse != null) tokenProvider.refreshToken(refreshToken);
        else throw new UsernameNotFoundException("Username: " + username + " Not Found.");

        return ResponseEntity.ok(tokenResponse);
    }
}
