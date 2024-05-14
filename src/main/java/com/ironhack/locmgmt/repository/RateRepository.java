package com.ironhack.locmgmt.repository;

import com.ironhack.locmgmt.model.Rate;
import com.ironhack.locmgmt.model.enums.Languages;
import com.ironhack.locmgmt.model.enums.ProjectType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {
    List<Rate> getRateBySourceLanguage(Languages sourceLanguage);

    List<Rate> getRateByTargetLanguage(Languages targetLanguage);

    List<Rate> findBySourceLanguageAndTargetLanguage(Languages sourceLanguage, Languages targetLanguage);

    List<Rate> findBySourceLanguageAndTargetLanguageAndProjectType(Languages sourceLanguage, Languages targetLanguage, ProjectType projectType);

    List<Rate> findByLinguistId(Long linguistId);
}
