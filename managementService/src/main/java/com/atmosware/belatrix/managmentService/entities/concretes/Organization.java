package com.atmosware.belatrix.managmentService.entities.concretes;

import com.atmosware.belatrix.managmentService.core.entities.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

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

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;
}
