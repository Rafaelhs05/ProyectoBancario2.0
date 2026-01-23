package app.web.controller;

import app.application.service.ServiceMotions;
import app.domain.model.StatusMotion;
import app.domain.model.TypeMotion;
import app.web.dto.RequestMotionDto;
import app.web.dto.ResponseMotionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/motion")
public class ControllerMotion {

    private final ServiceMotions serviceMotions;

    @GetMapping("/{status}")
    public ResponseEntity<List<ResponseMotionDto>> listaMotionStatus(@PathVariable("status") StatusMotion status) {

        return ResponseEntity.ok().body(serviceMotions.listMotionStateDone(status));
    }

    @GetMapping("/{originAccount}")
    public ResponseEntity<List<ResponseMotionDto>> listaMovimientoCuenta(@PathVariable String originAccount) {

        return ResponseEntity.ok().body(serviceMotions.listMotionByAccount(originAccount));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<ResponseMotionDto>> listaMovimientoTipo(@PathVariable("type") TypeMotion type) {

        return ResponseEntity.ok().body(serviceMotions.listMotionByType(type));
    }

    @PostMapping
    public ResponseEntity<ResponseMotionDto> registroMovimiento(@RequestBody RequestMotionDto requestMotionDto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(serviceMotions.createMotion(requestMotionDto));

    }

}
