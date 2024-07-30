package com.atmosware.belatrix.questionService.dataAccess;

import com.atmosware.belatrix.questionService.entities.concretes.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option,Long> {
}
