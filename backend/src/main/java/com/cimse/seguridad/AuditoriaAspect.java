package com.cimse.seguridad;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Aspect
@Component
public class AuditoriaAspect {

    @Autowired
    private AuditoriaRepository auditoriaRepository;

    // Esto intercepta CUALQUIER método que tenga la anotación @Auditable
    @AfterReturning(pointcut = "@annotation(auditable)", returning = "result")
    public void registrarAuditoria(JoinPoint joinPoint, Auditable auditable, Object result) {
        Auditoria audit = new Auditoria();
        audit.setAccion(auditable.accion());
        audit.setTablaAfectada(auditable.tabla());
        audit.setFechaHora(LocalDateTime.now());
        audit.setIdUsuario(1); // Por ahora el ID 1 (Admin). Luego lo sacaremos del Token JWT.
        audit.setDatosNuevos("Operación exitosa: " + joinPoint.getSignature().getName());
        
        auditoriaRepository.save(audit);
    }
}