package app.web.controllers;

import app.application.service.ServiceUser;

import app.web.dto.RequestUser;
import app.web.dto.ResponseUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class ControllerUser {

    private final ServiceUser serviceUser;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseUser> getUsuarioByid(@PathVariable("id") Long id) {

        return ResponseEntity.ok().body(serviceUser.getUsernameById(id));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<ResponseUser> getUsuarioByUsername(@PathVariable("username") String username) {

        return ResponseEntity.ok().body(serviceUser.getByUsername(username));
    }

    @PostMapping
    public ResponseEntity<ResponseUser> newUsuario(@RequestBody RequestUser requestUser) {

        return ResponseEntity.status(HttpStatus.CREATED).body(serviceUser.newUser(requestUser));
    }

}
