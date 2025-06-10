package com.caito.booksnapi.persistence.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * Represents a user in the application.
 * Implements UserDetails for Spring Security integration.
 * Contains personal information and account status.
 *
 * @author caito
 */
@Entity
@Table(name = "users")
@NoArgsConstructor@AllArgsConstructor
@Getter@Setter@Builder
@EntityListeners(AuditingEntityListener.class)
public class UserApp implements UserDetails, Principal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private LocalDate birthdate;
    @Column(unique = true)
    private String email;
    private String password;
    private boolean enabled;
    private boolean accontLocked;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Role> roles;
    @OneToMany(mappedBy = "owner")
    private List<Book> books;
    @OneToMany(mappedBy = "user")
    private List<BookTransactionHistory> histories;

    @Override
    public String getName() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .toList();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }


    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accontLocked;
    }

    public String fullname(){
        return firstname + " " + lastname;
    }
}
