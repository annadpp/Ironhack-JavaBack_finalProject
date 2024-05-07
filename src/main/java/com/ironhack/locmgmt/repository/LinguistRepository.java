package com.ironhack.locmgmt.repository;

import com.ironhack.locmgmt.model.Task;
import com.ironhack.locmgmt.model.enums.DTPTechnology;
import com.ironhack.locmgmt.model.enums.Languages;
import com.ironhack.locmgmt.model.enums.LinguisticTechnology;
import com.ironhack.locmgmt.model.enums.ProjectType;
import com.ironhack.locmgmt.model.users.Linguist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LinguistRepository extends JpaRepository<Linguist, Long> {
    List<Linguist> findBySourceLanguages(Languages sourceLanguage);

    List<Linguist> findByTargetLanguages(Languages targetLanguage);

    List<Linguist> findByProjectTypes(ProjectType projectTypes);

    List<Linguist> findByLinguisticTechnologies(LinguisticTechnology linguisticTechnology);

    List<Linguist> findByDtpTechnologies(DTPTechnology dtpTechnology);

    List<Linguist> findBySourceLanguagesAndTargetLanguages(Languages sourceLanguage, Languages targetLanguage);

    List<Linguist> findBySourceLanguagesAndTargetLanguagesAndProjectTypes(Languages sourceLanguages, Languages targetLanguages, ProjectType projectTypes);

    List<Linguist> findBySourceLanguagesAndTargetLanguagesAndLinguisticTechnologies(Languages sourceLanguages, Languages targetLanguages, LinguisticTechnology linguisticTechnology);

    List<Linguist> findBySourceLanguagesAndTargetLanguagesAndDtpTechnologies(Languages sourceLanguages, Languages targetLanguages, DTPTechnology dtpTechnology);

    List<Linguist> findByLinguisticTechnologiesAndProjectTypes(LinguisticTechnology linguisticTechnology, ProjectType projectTypes);

    List<Linguist> findByDtpTechnologiesAndProjectTypes(DTPTechnology dtpTechnology, ProjectType projectTypes);

    List<Linguist> findByUsername(String username);

    /*Add cheap/expensive rates filters*/
}
