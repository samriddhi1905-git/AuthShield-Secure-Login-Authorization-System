package authsystem.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "login_requests")
@Data
public class LoginRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @Column(unique = true, nullable = false)
    private String token;

    private boolean approved = false;

    // NEW: Store JWT after approval
    @Column(length = 1000)
    private String jwtToken;

    private LocalDateTime createdAt;

    private LocalDateTime expiresAt;
}