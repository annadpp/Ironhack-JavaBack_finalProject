package com.ironhack.locmgmt.repository;

import com.ironhack.locmgmt.model.enums.LinguisticTechnology;
import com.ironhack.locmgmt.model.projects.DTPProject;
import com.ironhack.locmgmt.model.projects.LinguisticProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface LinguisticProjectRepository extends JpaRepository<LinguisticProject, Long> {
   /* List<LinguisticProject> findByNewWordsLessThanAndFuzzyWordsLessThan(Integer newWords, Integer fuzzyWords);

    List<LinguisticProject> findByNewWordsGreaterThanAndFuzzyWordsGreaterThan(Integer newWords, Integer fuzzyWords);
*/
 /*   List<LinguisticProject> findByTotalWordsGreaterThan(Integer totalWords);

    List<LinguisticProject> findByTotalWordsLessThan(Integer totalWords);*/

/*    List<LinguisticProject> findByTotalBudgetGreaterThan(BigDecimal totalBudget);

    List<LinguisticProject> findByTotalBudgetLessThan(BigDecimal totalBudget);*/

    List<LinguisticProject> findByLinguisticTechnology(LinguisticTechnology linguisticTechnology);

    Optional<LinguisticProject> findByName(String name);
}