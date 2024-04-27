package com.ironhack.locmgmt.service;

import com.ironhack.locmgmt.model.projects.DTPProject;
import com.ironhack.locmgmt.repository.DTPProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DTPProjectService {

    @Autowired
    private DTPProjectRepository dtpProjectRepository;

    public List<DTPProject> getAllDTPProjects() {
        return dtpProjectRepository.findAll();
    }

    public DTPProject getDTPProjectById(Long id) {
        return dtpProjectRepository.findById(id).orElse(null);
    }

    public DTPProject createDTPProject(DTPProject DTPProject) {
        return dtpProjectRepository.save(DTPProject);
    }

    public DTPProject updateDTPProject(Long DTPProjectId, DTPProject dtpProjectDetails) {
        DTPProject existingDTPProject = dtpProjectRepository.findById(DTPProjectId)
                .orElseThrow(() -> new RuntimeException("DTPProject not found with id: " + DTPProjectId));

        existingDTPProject.setDtpTechnology(dtpProjectDetails.getDtpTechnology());
        existingDTPProject.setPages(dtpProjectDetails.getPages());

        return dtpProjectRepository.save(existingDTPProject);
    }

    public void deleteDTPProject(Long DTPProjectId) {
        dtpProjectRepository.deleteById(DTPProjectId);
    }
}
