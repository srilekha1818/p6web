// com.example.project.controller.ProjectController
package com.update.ctrl;
import org.springframework.stereotype.Controller;

import java.util.List;

//import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.p6.model.Project;
import com.p6.model.Project.ProjectRepository;
//com.example.project.controller.ProjectController

@Controller
public class ProjectController {

 @Autowired
 private ProjectRepository projectRepository;

 @Autowired
 private P6Service p6Service;

 @GetMapping("/updateProjectForm")
 public String showUpdateProjectForm(@RequestParam String projectName, Model model) {
	 //List<Project> project = projectRepository.findByProjectName(projectName);
             //.orElseThrow(() -> new NotFoundException("Project not found"));

     model.addAttribute("project", new Project());
     return "updateProjectForm";
 }

 @PostMapping("/updateProject")
 public String updateProject(@ModelAttribute Project project) {
     p6Service.updateProject(project);
     projectRepository.save(project);

     return "redirect:/updateProjectForm";
 }

 @GetMapping("/projectsByName")
 public String findProjectsByName(@RequestParam String projectName, Model model) {
     List<Project> projects = projectRepository.findByProjectName(projectName);
     model.addAttribute("projects", projects);
     return "projectList";
 }
}

