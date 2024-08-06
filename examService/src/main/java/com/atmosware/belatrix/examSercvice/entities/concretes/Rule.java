package com.atmosware.belatrix.examSercvice.entities.concretes;

import com.atmosware.belatrix.examSercvice.core.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "rules")
@SQLRestriction(value = "deleted_date is null")
public class Rule extends BaseEntity<Long> {
    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "rule",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<TestRule> testRules;
}
