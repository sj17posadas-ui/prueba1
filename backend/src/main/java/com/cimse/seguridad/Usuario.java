package com.cimse.seguridad;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario") // Mapeo al nombre real en la BD
    private Long id;

    @Column(name = "nombre_usuario", unique = true, nullable = false) // Mapeo
    private String username;

    @Column(name = "contrasena_hash", nullable = false) // Mapeo
    private String password;

    @Column(name = "rol")
    private String rol;

    // --- Métodos Getters y Setters (Cópialos tal cual) ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}