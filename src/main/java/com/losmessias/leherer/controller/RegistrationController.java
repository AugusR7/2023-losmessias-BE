package com.losmessias.leherer.controller;

import com.losmessias.leherer.dto.*;
import com.losmessias.leherer.service.JwtService;
import com.losmessias.leherer.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.token.TokenService;
import org.springframework.web.bind.annotation.*;



@CrossOrigin(origins = "*")
@RequestMapping("/api")
@RestController
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;
    private final JwtService jwtService;

    @PostMapping(path = "/registration")
    public String register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }

    @PostMapping(path = "/authentication")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(registrationService.authenticate(request));
    }

    @PostMapping(path = "/registration-professor")
    public ResponseEntity<String> registerProfessor(@RequestBody RegistrationProfessorRequest request) {
        return ResponseEntity.ok(registrationService.registerProfessor(request));
    }

    @GetMapping(path = "/registration/confirm")
    public ResponseEntity<AuthenticationResponse> confirm(@RequestParam("token") String token) {
        return ResponseEntity.ok(registrationService.confirmEmailToken(token));
    }

    @GetMapping(path = "/forgot_password/confirm")
    public ResponseEntity<String> confirmTokenForgotPassword(@RequestParam("token") String token) {
        return ResponseEntity.ok(registrationService.confirmChangePasswordToken(token));
    }

    @PostMapping(path = "/loadEmailForPasswordChange")
    public ResponseEntity<String> sendEmailForPasswordChange(@RequestParam("email") String email) {
        return ResponseEntity.ok(registrationService.sendEmailForPasswordChange(email));
    }

    @PostMapping(path = "/validate-email")
    public ResponseEntity<String> validateEmailNotTaken(@RequestParam("email") String email) {
        return ResponseEntity.ok(registrationService.validateEmailNotTaken(email));
    }

    @PostMapping(path = "/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody ForgotPasswordDto request) {
        String message = "{\"message\":" +
                "\"" + registrationService.changePassword(request)
                + "\"}";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    @GetMapping(path = "/is-token-expired")
    public ResponseEntity<String> isTokenExpired(@RequestParam String token) {
        if (jwtService.isTokenExpired(token)){
            return new ResponseEntity<>("false", HttpStatus.CONFLICT);
        }else{
            return new ResponseEntity<>("true", HttpStatus.OK);
        }
    }
}