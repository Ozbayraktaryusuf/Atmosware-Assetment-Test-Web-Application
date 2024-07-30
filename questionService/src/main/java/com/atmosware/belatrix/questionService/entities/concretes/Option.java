package com.atmosware.belatrix.questionService.entities.concretes;

import com.atmosware.belatrix.questionService.core.entities.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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
@Table(name = "options")
@SQLRestriction(value = "deleted_date is null")
public class Option extends BaseEntity<Long> {
    @Column
    @Size(max = 500)
    private String text;

    @ManyToOne
    @JoinColumn(name = "question_id",nullable = false)
    private Question question;

    @Column(name = "correct")
    private boolean correct;
}
