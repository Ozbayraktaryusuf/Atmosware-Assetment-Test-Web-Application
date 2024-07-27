package com.atmosware.belatrix.managmentService.entities.concretes;

import com.atmosware.belatrix.managmentService.core.entities.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "roles")
@SQLRestriction(value = "deleted_date is null")
public class Roles extends BaseEntity<UUID> implements GrantedAuthority {

    private String name;

    @OneToMany(mappedBy = "role",cascade = CascadeType.ALL)
    private List<UserRole> userRoles;

    public Roles(UUID id){
        setId(id);
    }

    @Override
    public String getAuthority() {
        return getName();
    }
}
