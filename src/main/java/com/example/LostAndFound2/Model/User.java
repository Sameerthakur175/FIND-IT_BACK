package com.example.LostAndFound2.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Data
public class User implements UserDetails {
    @Id
    @Column(name = "email_id", nullable = false, unique = true)
    private String emailId;

    private String name;

    private String mobileNo;

    private String password;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { return Collections.emptyList(); }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() { return this.emailId; }
    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return true; }


}
