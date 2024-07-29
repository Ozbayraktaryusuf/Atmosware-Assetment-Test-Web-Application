package com.atmosware.belatrix.managmentService.entities.concretes;

import com.atmosware.belatrix.managmentService.core.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "organizations")
@SQLRestriction(value = "deleted_date is null")
public class Organization extends BaseEntity<UUID> {
    @Column(name = "organization_name")
    private String organizationName;

    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL)
    private List<User> userList;

    public Organization(UUID id) {
         setId(id);
    }
}

