package com.atmosware.belatrix.managmentService.entities.concretes;

import com.atmosware.belatrix.managmentService.core.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
@SQLRestriction(value = "deleted_date is null")
public class User extends BaseEntity<UUID> implements UserDetails {

    private String password;

    private String email;

    @ManyToOne()
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER ,cascade = CascadeType.ALL)
    private List<UserRole> authorities;

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.authorities==null)
            return new HashSet<>();
        return authorities.stream().
                map(UserRole::getRole)
                .toList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
