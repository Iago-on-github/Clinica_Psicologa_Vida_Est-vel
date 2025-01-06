package br.com.psicoclinic.Models;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "permission")
public class Permissions implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String description;

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String getAuthority() {
        return description;
    }
}
