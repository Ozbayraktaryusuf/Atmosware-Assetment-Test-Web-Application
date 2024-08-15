package com.atmosware.belatrix.questionService.entities.concretes;

import com.atmosware.belatrix.questionService.core.entities.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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
@Table(name = "questions")
@SQLRestriction(value = "deleted_date is null")
public class Question extends BaseEntity<Long> {
    @Column
    @Size(max = 2000,min = 10)
    private String text;

    @OneToMany(mappedBy = "question",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Size(min = 2, max = 5, message = "A question must have between 2 and 5 options")
    private List<Option> options;

    @Column(name = "organization_id")
    private UUID organizationId;

}
