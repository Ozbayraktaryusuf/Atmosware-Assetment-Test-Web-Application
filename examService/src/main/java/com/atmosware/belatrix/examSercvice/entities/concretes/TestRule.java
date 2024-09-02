package com.atmosware.belatrix.examSercvice.entities.concretes;

import com.atmosware.belatrix.examSercvice.core.entities.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "test_rules")
@SQLRestriction(value = "deleted_date is null")
public class TestRule extends BaseEntity<Long> {
    @ManyToOne()
    @JoinColumn(name = "rule_id",nullable = false)
    private Rule rule;

    @ManyToOne()
    @JoinColumn(name = "test_id",nullable = false)
    private Test test;
}
