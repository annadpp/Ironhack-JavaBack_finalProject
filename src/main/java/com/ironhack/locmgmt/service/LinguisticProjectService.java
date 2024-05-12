package com.ironhack.locmgmt.service;

import com.ironhack.locmgmt.exception.EmptyListException;
import com.ironhack.locmgmt.model.Client;
import com.ironhack.locmgmt.model.Task;
import com.ironhack.locmgmt.model.enums.LinguisticTechnology;
import com.ironhack.locmgmt.model.enums.Status;
import com.ironhack.locmgmt.model.projects.DTPProject;
import com.ironhack.locmgmt.model.projects.LinguisticProject;
import com.ironhack.locmgmt.model.users.ProjectManager;
import com.ironhack.locmgmt.repository.ClientRepository;
import com.ironhack.locmgmt.repository.LinguisticProjectRepository;
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

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.ironhack.locmgmt.util.ProjectUtil.updateProjectDates;

@Service
public class LinguisticProjectService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DTPProjectService.class);

    @Autowired
    private LinguisticProjectRepository linguisticProjectRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProjectManagerRepository projectManagerRepository;

    @Autowired
    private TaskRepository taskRepository;

    public List<LinguisticProject> getAllLinguisticProjects() {
        try {List<LinguisticProject> linguisticProjects = linguisticProjectRepository.findAll();
            if (linguisticProjects.isEmpty()) {
                throw new EmptyListException("No Linguistic projects were found");
            }
            for (LinguisticProject linguisticProject : linguisticProjects) {
                ProjectUtil.updateTimeRemaining(linguisticProject);
            }
            return linguisticProjects;
        } catch (DataAccessException e) {
            throw new DataRetrievalFailureException("Error while retrieving all Linguistic projects", e);
        }
    }

    public LinguisticProject getLinguisticProjectById(Long id) {
        return linguisticProjectRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Admin not found with id: " + id));
    }

    @Transactional
    public LinguisticProject createLinguisticProject(LinguisticProject linguisticProject) {
        //Checks if there is already a project with the same name
        Optional<LinguisticProject> existingProject = linguisticProjectRepository.findByName(linguisticProject.getName());
        if (existingProject.isPresent()) {
            throw new DataIntegrityViolationException("A project with the same name already exists");
        }

        //Check if tasks are being set directly
      /*  if (!linguisticProject.getTasks().isEmpty()) {
            LOGGER.info("Tasks cannot be assigned directly to projects. Add the project information in the task itself.");
        }*/

        // Si el cliente se proporciona, carga explícitamente toda la información del cliente
        if (linguisticProject.getClient() != null) {
            Client client = clientRepository.findById(linguisticProject.getClient().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + linguisticProject.getClient().getId()));
            linguisticProject.setClient(client);
        }

        // Si el ProjectManager se proporciona, carga explícitamente toda la información del ProjectManager
        if (linguisticProject.getProjectManager() != null) {
            ProjectManager projectManager = projectManagerRepository.findById(linguisticProject.getProjectManager().getId())
                    .orElseThrow(() -> new EntityNotFoundException("ProjectManager not found with id: " + linguisticProject.getProjectManager().getId()));
            linguisticProject.setProjectManager(projectManager);
        }

        //Sets tasks to empty lists
        linguisticProject.setTasks(Collections.emptyList());

        //Sets projectStatus to NOT if info not passed by the user when creating project
        linguisticProject.setProjectStatus(linguisticProject.getProjectStatus() != null ? linguisticProject.getProjectStatus() : Status.NOT_STARTED);

        //Update project dates and margins
        ProjectUtil.updateProjectDates(linguisticProject);
        ProjectUtil.calculateMargin(linguisticProject);
        ProjectUtil.calculateMarginPercentage(linguisticProject);

        try {
            return linguisticProjectRepository.save(linguisticProject);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Error while creating the Linguistic project", e);
        }
    }

    @Transactional
    public LinguisticProject updateLinguisticProject(Long linguisticProjectId, LinguisticProject linguisticProjectDetails) {
        LinguisticProject existingLinguisticProject = linguisticProjectRepository.findById(linguisticProjectId)
                .orElseThrow(() -> new RuntimeException("Linguistic project not found with id: " + linguisticProjectId));

        if (existingLinguisticProject.getProjectStatus() == Status.FINISHED) {
            //If linguistic project status is FINISHED, only project status can be changed.
            if (linguisticProjectDetails.getProjectStatus() != Status.FINISHED) {
                throw new IllegalArgumentException("Cannot update fields other than project status when the project is FINISHED.");
            }
        }

        //Update the inherent DTP Project fields passed
        if (linguisticProjectDetails.getLinguisticTechnology() != null) {
            existingLinguisticProject.setLinguisticTechnology(linguisticProjectDetails.getLinguisticTechnology());
        }

        //Update the fields inherited from the Project class
        if (linguisticProjectDetails.getName() != null) {
            Optional<LinguisticProject> existingProjectWithSameName = linguisticProjectRepository.findByName(linguisticProjectDetails.getName());
            if (existingProjectWithSameName.isPresent()) {
                throw new DataIntegrityViolationException("A project with the same name already exists");
            }
            existingLinguisticProject.setName(linguisticProjectDetails.getName());
        }
        if (linguisticProjectDetails.getDescription() != null) {
            existingLinguisticProject.setDescription(linguisticProjectDetails.getDescription());
        }
        if (linguisticProjectDetails.getDeadline() != null) {
            existingLinguisticProject.setDeadline(linguisticProjectDetails.getDeadline());
            ProjectUtil.updateTimeRemaining(existingLinguisticProject);
        }
        if (linguisticProjectDetails.getProjectStatus() != null) {
            existingLinguisticProject.setProjectStatus(linguisticProjectDetails.getProjectStatus());
            updateProjectDates(existingLinguisticProject);
        }
        if (linguisticProjectDetails.getTotalBudget() != null) {
            existingLinguisticProject.setTotalBudget(linguisticProjectDetails.getTotalBudget());
            ProjectUtil.calculateMargin(existingLinguisticProject);
            ProjectUtil.calculateMarginPercentage(existingLinguisticProject);
        }
        if (linguisticProjectDetails.getProjectStatus() != null) {
            existingLinguisticProject.setProjectStatus(linguisticProjectDetails.getProjectStatus());
        }
        //Add client when updating project
        if (linguisticProjectDetails.getClient() != null) {
            Client client = clientRepository.findById(linguisticProjectDetails.getClient().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + linguisticProjectDetails.getClient().getId()));
            existingLinguisticProject.setClient(client);
        }
        //Add project manager when updating DTP project
        if (linguisticProjectDetails.getProjectManager() != null) {
            ProjectManager projectManager = projectManagerRepository.findById(linguisticProjectDetails.getProjectManager().getId())
                    .orElseThrow(() -> new EntityNotFoundException("ProjectManager not found with id: " + linguisticProjectDetails.getProjectManager().getId()));
            existingLinguisticProject.setProjectManager(projectManager);
        }

        return linguisticProjectRepository.save(existingLinguisticProject);
    }

    @Transactional
    public void deleteLinguisticProject(Long id) {
        try {
            //Get all tasks assigned to the project we are going to delete
            List<Task> tasks = taskRepository.findByProjectId(id);

            //Delete all tasks assigned to the project
            for (Task task : tasks) {
                taskRepository.delete(task);
            }

            //Delete the project
            linguisticProjectRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Linguistic project not found with id: " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Error deleting Linguistic project with id: " + id);
        }
    }

    public List<LinguisticProject> findByLinguisticTechnology(LinguisticTechnology linguisticTechnology) {
        if (linguisticTechnology == null) {
            throw new IllegalArgumentException("LinguisticTechnology must be provided.");
        }
        List<LinguisticProject> projects = linguisticProjectRepository.findByLinguisticTechnology(linguisticTechnology);
        if (projects.isEmpty()) {
            throw new EmptyListException("No linguistic projects found with provided criteria.");
        }
        return projects;
    }
}
