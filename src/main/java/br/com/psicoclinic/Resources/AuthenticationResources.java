package br.com.psicoclinic.Resources;

import br.com.psicoclinic.Models.VO.AccountCredentialsVO;
import br.com.psicoclinic.Service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationResources {
    private final AuthenticationService authenticationService;

    public AuthenticationResources(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signing")
    public ResponseEntity signing(@RequestBody AccountCredentialsVO data) {
        if (checkIfParamsIsNotNull(data)) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Missing username or password.");

        var token = authenticationService.signin(data);
        if (token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Incorrect client request.");

        return ResponseEntity.ok().body(token);
    }

    @PutMapping("/refresh/{username}")
    public ResponseEntity refreshToken(@PathVariable("username") String username, @RequestHeader("Authorization") String refreshToken) {
        if (checkIfParamsIsNotNull(username, refreshToken)) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Missing username or password.");

        var token = authenticationService.refreshToken(username, refreshToken);
        if (token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Incorrect client request.");

        return ResponseEntity.ok().body(token);
    }


    private boolean checkIfParamsIsNotNull(AccountCredentialsVO vo) {
        return vo == null || vo.getUsername() == null || vo.getUsername().isBlank()
                || vo.getPassword() == null || vo.getPassword().isBlank();
    }

    private boolean checkIfParamsIsNotNull(String username, String refreshToken) {
        return username == null || username.isBlank() || refreshToken == null || refreshToken.isBlank();
    }
}
