package com.cimse.personal;

import com.cimse.seguridad.Auditable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personal")
public class EmpleadoController {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @GetMapping
    public List<Empleado> listar() {
        return empleadoRepository.findAll();
    }

    //@Auditable(accion = "CREAR", tabla = "empleados")
    @PostMapping
    public Empleado crear(@RequestBody Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empleado> buscar(@PathVariable Long id) {
        return empleadoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Auditable(accion = "ACTUALIZAR", tabla = "empleados")
    @PutMapping("/{id}")
    public ResponseEntity<Empleado> actualizar(@PathVariable Long id, @RequestBody Empleado empleadoDetalles) {
        return empleadoRepository.findById(id).map(empleado -> {
            empleado.setNombreCompleto(empleadoDetalles.getNombreCompleto());
            empleado.setTelefono(empleadoDetalles.getTelefono());
            empleado.setCargo(empleadoDetalles.getCargo());
            empleado.setSueldoBase(empleadoDetalles.getSueldoBase());
            return ResponseEntity.ok(empleadoRepository.save(empleado));
        }).orElse(ResponseEntity.notFound().build());
    }
}