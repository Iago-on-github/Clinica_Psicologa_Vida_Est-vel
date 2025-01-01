package br.com.psicoclinic.Resources;

import br.com.psicoclinic.Models.Dtos.ResponsePatientDto;
import br.com.psicoclinic.Models.VO.AccountCredentialsVO;
import br.com.psicoclinic.Service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "Authenticate",
            description = "Method for authenticate a clients and return a token")
    public ResponseEntity signing(@RequestBody AccountCredentialsVO data) {
        if (checkIfParamsIsNotNull(data)) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Missing username or password.");

        var token = authenticationService.signin(data);
        if (token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Incorrect client request.");

        return ResponseEntity.ok().body(token);
    }

    @PutMapping("/refresh/{username}")
    @Operation(summary = "Refresh Token",
            description = "Method for refresh token and authenticate")
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
