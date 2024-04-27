package com.ironhack.locmgmt.service;

import com.ironhack.locmgmt.model.projects.LinguisticProject;
import com.ironhack.locmgmt.repository.LinguisticProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinguisticProjectService {

    @Autowired
    private LinguisticProjectRepository linguisticProjectRepository;

    public List<LinguisticProject> getAllLinguisticProjects() {
        return linguisticProjectRepository.findAll();
    }

    public LinguisticProject getLinguisticProjectById(Long id) {
        return linguisticProjectRepository.findById(id).orElse(null);
    }

    public LinguisticProject createLinguisticProject(LinguisticProject DTPProject) {
        return linguisticProjectRepository.save(DTPProject);
    }

    public LinguisticProject updateLinguisticProject(Long linguisticProjectId, LinguisticProject linguisticProjectDetails) {
        LinguisticProject existingLinguisticProject = linguisticProjectRepository.findById(linguisticProjectId)
                .orElseThrow(() -> new RuntimeException("LinguisticProject not found with id: " + linguisticProjectId));

        existingLinguisticProject.setNewWords(linguisticProjectDetails.getNewWords());
        existingLinguisticProject.setFuzzyWords(linguisticProjectDetails.getFuzzyWords());
        existingLinguisticProject.setLinguisticTechnology(linguisticProjectDetails.getLinguisticTechnology());
        existingLinguisticProject.setSourceLanguage(linguisticProjectDetails.getSourceLanguage());
        existingLinguisticProject.setTargetLanguages(linguisticProjectDetails.getTargetLanguages());

        return linguisticProjectRepository.save(existingLinguisticProject);
    }

    public void deleteLinguisticProject(Long LinguisticProjectId) {
        linguisticProjectRepository.deleteById(LinguisticProjectId);
    }
}
