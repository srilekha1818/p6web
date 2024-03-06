package com.p6.p6web18;


import com.p6.p6web18.P6Service;
import com.p6.p6web18.Project;

import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    private final P6Service p6Service;
    public Project project1;

    @Autowired
    public ProjectController(P6Service p6Service) {
        this.p6Service = p6Service;
    }

    @GetMapping("/form")
    
    public String showForm(Model model) {
        model.addAttribute("project", new Project());
        return "create";
    }

    @GetMapping("/formpro")
    public String showFormread(Model model) {
        model.addAttribute("project", new Project());
        return "projects";
    }
    
    /*@PostMapping(value = "/create", consumes = "application/json")
    @ResponseBody
    public String createProject(@RequestBody List<Project> projects) throws URISyntaxException {
        for (Project project : projects) {
            p6Service.createProject(project);
        }

        return "Project(s) created successfully!";
    }*/
   
    @PostMapping(path = "/create")
    @ResponseBody
    public ResponseEntity<String> createProject(@ModelAttribute Project project) throws URISyntaxException {
        try {
            p6Service.createProject(project);
            return ResponseEntity.ok("Project created successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating project");
        }
    }
    
   
   @GetMapping("/projects-by-eps")
    @ResponseBody
    public String getProjectsByEPSId(@RequestParam Long epsId,@ModelAttribute Project project) throws URISyntaxException {
        return p6Service.getProjectsByEPSId(epsId,project);
    }
    
    @GetMapping("/update")
    public String showUpdateForm( Model model) {
        model.addAttribute("project", new Project());
        return "update";
    }

    @PostMapping(path = "/updated")
    @ResponseBody
    public  String updateProject(@ModelAttribute Project project) throws URISyntaxException {
            p6Service.updateProject(project);
       

        return "project updated successfully!";
    }


    @GetMapping("/delete/{projectId}")
    @ResponseBody
    public String deleteProject(@PathVariable Long projectId) {
        p6Service.deleteProject(projectId);
        return "Project deleted successfully!";
    }
}
