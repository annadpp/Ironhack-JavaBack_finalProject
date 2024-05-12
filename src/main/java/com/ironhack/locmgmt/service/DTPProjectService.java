package com.ironhack.locmgmt.service;

import com.ironhack.locmgmt.exception.EmptyListException;
import com.ironhack.locmgmt.model.Client;
import com.ironhack.locmgmt.model.Task;
import com.ironhack.locmgmt.model.enums.DTPTechnology;
import com.ironhack.locmgmt.model.enums.Status;
import com.ironhack.locmgmt.model.projects.DTPProject;
import com.ironhack.locmgmt.model.users.ProjectManager;
import com.ironhack.locmgmt.repository.ClientRepository;
import com.ironhack.locmgmt.repository.DTPProjectRepository;
import com.ironhack.locmgmt.repository.ProjectManagerRepository;
import com.ironhack.locmgmt.repository.TaskRepository;
import com.ironhack.locmgmt.util.ProjectUtil;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.ironhack.locmgmt.util.ProjectUtil.updateProjectDates;

@Service
public class DTPProjectService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DTPProjectService.class);

    @Autowired
    private DTPProjectRepository dtpProjectRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProjectManagerRepository projectManagerRepository;

    @Autowired
    private TaskRepository taskRepository;

    public List<DTPProject> getAllDTPProjects() {
        try {
            List<DTPProject> dtpProjects = dtpProjectRepository.findAll();
            if (dtpProjects.isEmpty()) {
                throw new EmptyListException("No DTP projects were found");
            }
            for (DTPProject dtpProject : dtpProjects) {
                ProjectUtil.updateTimeRemaining(dtpProject);
            }
            return dtpProjects;
        } catch (DataAccessException e) {
            throw new DataRetrievalFailureException("Error while retrieving all DTP projects", e);
        }
    }

    public DTPProject getDTPProjectById(Long id) {
        return dtpProjectRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("DTP project not found with id: " + id));
    }

    @Transactional
    public DTPProject createDTPProject(DTPProject dtpProject) {
        //Checks if there is already a project with the same name
        Optional<DTPProject> existingProject = dtpProjectRepository.findByName(dtpProject.getName());
        if (existingProject.isPresent()) {
            throw new DataIntegrityViolationException("A project with the same name already exists");
        }

        //Check if tasks are being set directly
        /*if (!dtpProject.getTasks().isEmpty()) {
            LOGGER.info("Tasks cannot be assigned directly to projects. Add the project information in the task itself.");
        }*/

        //Set tasks to empty lists
        dtpProject.setTasks(Collections.emptyList());

        // Si el cliente se proporciona, carga explícitamente toda la información del cliente
        if (dtpProject.getClient() != null) {
            Client client = clientRepository.findById(dtpProject.getClient().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + dtpProject.getClient().getId()));
            dtpProject.setClient(client);
        }

        // Si el ProjectManager se proporciona, carga explícitamente toda la información del ProjectManager
        if (dtpProject.getProjectManager() != null) {
            ProjectManager projectManager = projectManagerRepository.findById(dtpProject.getProjectManager().getId())
                    .orElseThrow(() -> new EntityNotFoundException("ProjectManager not found with id: " + dtpProject.getProjectManager().getId()));
            dtpProject.setProjectManager(projectManager);
        }

        // Sets projectStatus to NOT if info not passed by the user when creating project
        dtpProject.setProjectStatus(dtpProject.getProjectStatus() != null ? dtpProject.getProjectStatus() : Status.NOT_STARTED);

        // Update project dates and margins
        ProjectUtil.updateProjectDates(dtpProject);
        ProjectUtil.calculateMargin(dtpProject);
        ProjectUtil.calculateMarginPercentage(dtpProject);

        try {
            return dtpProjectRepository.save(dtpProject);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Error while creating the DTP project", e);
        }
    }

    @Transactional
    public DTPProject updateDTPProject(Long DTPProjectId, DTPProject dtpProjectDetails) {
        DTPProject existingDTPProject = dtpProjectRepository.findById(DTPProjectId)
                .orElseThrow(() -> new RuntimeException("DTP project not found with id: " + DTPProjectId));

        if (existingDTPProject.getProjectStatus() == Status.FINISHED) {
            //If DTP project status is FINISHED, only project status can be changed.
            if (dtpProjectDetails.getProjectStatus() != Status.FINISHED) {
                throw new IllegalArgumentException("Cannot update fields other than project status when the project is FINISHED.");
            }
        }

        //Update the inherent DTP Project fields passed
        if (dtpProjectDetails.getDtpTechnology() != null) {
            existingDTPProject.setDtpTechnology(dtpProjectDetails.getDtpTechnology());
        }

        //Update the fields inherited from the Project class
        if (dtpProjectDetails.getName() != null && !dtpProjectDetails.getName().equals(existingDTPProject.getName())) {
            Optional<DTPProject> existingProjectWithSameName = dtpProjectRepository.findByName(dtpProjectDetails.getName());
            if (existingProjectWithSameName.isPresent()) {
                throw new DataIntegrityViolationException("A project with the same name already exists");
            }
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
            ProjectUtil.calculateMargin(existingDTPProject);
            ProjectUtil.calculateMarginPercentage(existingDTPProject);
        }
        if (dtpProjectDetails.getProjectStatus() != null) {
            existingDTPProject.setProjectStatus(dtpProjectDetails.getProjectStatus());
        }

        //Add client when updating DTP project
        if (dtpProjectDetails.getClient() != null) {
            Client client = clientRepository.findById(dtpProjectDetails.getClient().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + dtpProjectDetails.getClient().getId()));
            existingDTPProject.setClient(client);
        }

        //Add project manager when updating DTP project
        if (dtpProjectDetails.getProjectManager() != null) {
            ProjectManager projectManager = projectManagerRepository.findById(dtpProjectDetails.getProjectManager().getId())
                    .orElseThrow(() -> new EntityNotFoundException("ProjectManager not found with id: " + dtpProjectDetails.getProjectManager().getId()));
            existingDTPProject.setProjectManager(projectManager);
        }

        return dtpProjectRepository.save(existingDTPProject);
    }

    @Transactional
    public void deleteDTPProject(Long id) {
        try {
            //Get all tasks assigned to the project we are going to delete
            List<Task> tasks = taskRepository.findByProjectId(id);

            //Delete all tasks assigned to the project
            for (Task task : tasks) {
                taskRepository.delete(task);
            }

            //Delete the project
            dtpProjectRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Project not found with id: " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Error deleting project with id: " + id);
        }
    }

    public List<DTPProject> findByDtpTechnology(DTPTechnology dtpTechnology) {
        List<DTPProject> projects = dtpProjectRepository.findByDtpTechnology(dtpTechnology);
        if (projects.isEmpty()) {
            throw new EmptyListException("No DTP projects found with DTP technology: " + dtpTechnology);
        }
        return projects;
    }
}
