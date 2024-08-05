package com.atmosware.belatrix.examSercvice.entities.concretes;

import com.atmosware.belatrix.examSercvice.core.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tests")
@SQLRestriction(value = "deleted_date is null")
public class Test extends BaseEntity<Long> {

    @Column(name = "organization_id")
    private UUID organizationId;

    @Column(name = "exam_name")
    private String examName;

    @Column(name = "start_date",nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date",nullable = false)
    private LocalDateTime endDate;

    @Column(name = "duration", nullable = false)
    private Long duration;

    @OneToMany(mappedBy = "test", fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<TestQuestion> testQuestions;

    @OneToMany(mappedBy = "test",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<TestRule> testRules;
}
