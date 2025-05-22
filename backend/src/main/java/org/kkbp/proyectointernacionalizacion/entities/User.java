package org.kkbp.proyectointernacionalizacion.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "app_user")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    @NotBlank
    @Size(max = 100)
    private String names;

    @Column(nullable = false)
    @NotBlank
    @Size(max = 100)
    private String surnames;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false, unique = true)
    @NotBlank
    @Email
    private String email;

    @Column(nullable = false)
    @NotBlank
    private String password;

    @OneToOne
    @JoinColumn(name = "coordinated_career_id")
    private Career coordinatedCareer;

    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;
}
