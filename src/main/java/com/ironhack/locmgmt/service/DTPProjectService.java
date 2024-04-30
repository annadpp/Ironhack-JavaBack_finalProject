package com.ironhack.locmgmt.service;

import com.ironhack.locmgmt.exception.EmptyListException;
import com.ironhack.locmgmt.model.projects.DTPProject;
import com.ironhack.locmgmt.repository.DTPProjectRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DTPProjectService {

    @Autowired
    private DTPProjectRepository dtpProjectRepository;

    public List<DTPProject> getAllDTPProjects() {
        try {List<DTPProject> admins = dtpProjectRepository.findAll();
            if (admins.isEmpty()) {
                throw new EmptyListException("No DTP projects were found");
            }
            return admins;
        } catch (DataAccessException e) {
            throw new DataRetrievalFailureException("Error while retrieving all DTP projects", e);
        }
    }

    public DTPProject getDTPProjectById(Long id) {
        return dtpProjectRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("DTP project not found with id: " + id));
    }

    /*projectstatus base not_started*/
    public DTPProject createDTPProject(DTPProject DTPProject) {
        try {
            return dtpProjectRepository.save(DTPProject);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Error while creating the DTP project", e);
        }
    }

    public DTPProject updateDTPProject(Long DTPProjectId, DTPProject dtpProjectDetails) {
        DTPProject existingDTPProject = dtpProjectRepository.findById(DTPProjectId)
                .orElseThrow(() -> new RuntimeException("DTP project not found with id: " + DTPProjectId));

        //Update the inherent DTP Project fields passed
        if (dtpProjectDetails.getDtpTechnology() != null) {
            existingDTPProject.setDtpTechnology(dtpProjectDetails.getDtpTechnology());
        }
        if (dtpProjectDetails.getPages() != null) {
            existingDTPProject.setPages(dtpProjectDetails.getPages());
        }

        //Update the fields inherited from the Project class
        if (dtpProjectDetails.getName() != null) {
            existingDTPProject.setName(dtpProjectDetails.getName());
        }
        if (dtpProjectDetails.getDescription() != null) {
            existingDTPProject.setDescription(dtpProjectDetails.getDescription());
        }
        if (dtpProjectDetails.getTotalBudget() != null) {
            existingDTPProject.setTotalBudget(dtpProjectDetails.getTotalBudget());
        }
        if (dtpProjectDetails.getProjectStatus() != null) {
            existingDTPProject.setProjectStatus(dtpProjectDetails.getProjectStatus());
        }
        if (dtpProjectDetails.getSourceLanguage() != null) {
            existingDTPProject.setSourceLanguage(dtpProjectDetails.getSourceLanguage());
        }
        if (dtpProjectDetails.getTargetLanguages() != null) {
            existingDTPProject.setTargetLanguages(dtpProjectDetails.getTargetLanguages());
        }

        return dtpProjectRepository.save(existingDTPProject);
    }

    public void deleteDTPProject(Long id) {
        try {
            dtpProjectRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("DTP project not found with id: " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Error deleting DTP project with id: " + id);
        }
    }
}
