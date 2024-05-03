package com.ironhack.locmgmt.service;

import com.ironhack.locmgmt.exception.EmptyListException;
import com.ironhack.locmgmt.model.enums.DTPTechnology;
import com.ironhack.locmgmt.model.enums.Status;
import com.ironhack.locmgmt.model.projects.DTPProject;
import com.ironhack.locmgmt.repository.DTPProjectRepository;
import com.ironhack.locmgmt.util.ProjectUtil;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static com.ironhack.locmgmt.util.ProjectUtil.updateProjectDates;

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

    public DTPProject createDTPProject(DTPProject DTPProject) {
        // Set tasks to empty lists
        DTPProject.setTasks(Collections.emptyList());

        /*Add "Projects cannot be assigned directly to tasks or linguists" if we have time*/

        //Sets projectStatus to NOT if info not passed by the user when creating project
        DTPProject.setProjectStatus(DTPProject.getProjectStatus() != null ? DTPProject.getProjectStatus() : Status.NOT_STARTED);

        //Update project dates and time remaining
        ProjectUtil.updateProjectDates(DTPProject);

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
        if (dtpProjectDetails.getDeadline() != null) {
            existingDTPProject.setDeadline(dtpProjectDetails.getDeadline());
            ProjectUtil.updateTimeRemaining(existingDTPProject);
        }
        if (dtpProjectDetails.getProjectStatus() != null) {
            existingDTPProject.setProjectStatus(dtpProjectDetails.getProjectStatus());
            updateProjectDates(existingDTPProject);
        }
        if (dtpProjectDetails.getTotalBudget() != null) {
            existingDTPProject.setTotalBudget(dtpProjectDetails.getTotalBudget());
        }
        if (dtpProjectDetails.getProjectStatus() != null) {
            existingDTPProject.setProjectStatus(dtpProjectDetails.getProjectStatus());
        }

        //Add client when updating DTP project
        if (dtpProjectDetails.getClient() != null) {
            existingDTPProject.setClient(dtpProjectDetails.getClient());
        }
        //Add project manager when updating DTP project
        if (dtpProjectDetails.getProjectManager() != null) {
            existingDTPProject.setProjectManager(dtpProjectDetails.getProjectManager());
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

    public List<DTPProject> findByDtpTechnology(DTPTechnology dtpTechnology) {
        List<DTPProject> projects = dtpProjectRepository.findByDtpTechnology(dtpTechnology);
        if (projects.isEmpty()) {
            throw new EmptyListException("No DTP projects found with DTP technology: " + dtpTechnology);
        }
        return projects;
    }

    public List<DTPProject> findByPagesGreaterThan(Integer pages) {
        List<DTPProject> projects = dtpProjectRepository.findByPagesGreaterThan(pages);
        if (projects.isEmpty()) {
            throw new EmptyListException("No DTP projects found with pages greater than: " + pages);
        }
        return projects;
    }

    public List<DTPProject> findByPagesLessThan(Integer pages) {
        List<DTPProject> projects = dtpProjectRepository.findByPagesLessThan(pages);
        if (projects.isEmpty()) {
            throw new EmptyListException("No DTP projects found with pages less than: " + pages);
        }
        return projects;
    }
}
