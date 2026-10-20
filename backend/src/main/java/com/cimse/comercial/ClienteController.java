package com.cimse.comercial;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteRepository repo;

    public ClienteController(ClienteRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Cliente> listar() {
        return repo.findAll();
    }

    @PostMapping
    public Cliente crear(@RequestBody Cliente cliente) {
        cliente.setIdCliente(null);
        return repo.save(cliente);
    }
}
