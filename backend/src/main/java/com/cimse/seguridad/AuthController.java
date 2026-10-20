package com.cimse.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 👇 Fíjate que aquí ya NO inyectamos el AuditoriaRepository, ni lo usamos manualmente.

    // 👇 ESTA ES LA MAGIA DEL AOP: Solo ponemos la anotación encima del endpoint
    @Auditable(accion = "LOGIN", tabla = "usuarios")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(username);

        if (usuarioOpt.isPresent() && passwordEncoder.matches(password, usuarioOpt.get().getPassword())) {
            Usuario usuario = usuarioOpt.get();
            String token = jwtUtil.generateToken(usuario.getUsername(), usuario.getRol());
            
            // El código de auditoría ya no va aquí, el Aspecto lo guarda solo en la BD.

            return ResponseEntity.ok(Map.of(
                "mensaje", "Login exitoso",
                "token", token,
                "rol", usuario.getRol()
            ));
        }

        return ResponseEntity.status(401).body(Map.of("error", "Credenciales incorrectas"));
    }
}