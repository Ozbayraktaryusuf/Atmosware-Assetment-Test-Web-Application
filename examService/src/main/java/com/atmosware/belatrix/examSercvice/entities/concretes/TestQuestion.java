package com.atmosware.belatrix.examSercvice.entities.concretes;

import com.atmosware.belatrix.examSercvice.core.entities.BaseEntity;
import jakarta.persistence.*;
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
@Table(name = "test_questions")
@SQLRestriction(value = "deleted_date is null")
public class TestQuestion extends BaseEntity<Long> {
    @Column(name = "question_id",nullable = false)
    private Long questionId;

    @ManyToOne
    @JoinColumn(name = "test_id",nullable = false)
    private Test test;
}
