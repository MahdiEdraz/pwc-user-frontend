package com.pwc.user.frontend.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import com.pwc.user.frontend.model.Department;
import com.pwc.user.frontend.model.Project;

@ManagedBean(name = "projectController")
@SessionScoped
public class ProjectController  implements Serializable{
	private List<Project> projectsDetails = new ArrayList();
	private String newProject = "";

	@PostConstruct
	public void init() {

	}

	public void onRowEdit(RowEditEvent event) {
		Project project = (Project) event.getObject();
		FacesMessage msg = new FacesMessage("project Edited", String.valueOf(project.getId()));
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
  

	public List<Project> getProjectsDetails() {
		return projectsDetails;
	}

	public void setProjectsDetails(List<Project> projectsDetails) {
		this.projectsDetails = projectsDetails;
	}

	public String getNewProject() {
		return newProject;
	}

	public void setNewProject(String newproject) {
		this.newProject = newproject;
	}

	public void createProject() {
		System.out.println(newProject);
	}

	public void updateproject(Project project, int index) {
		Project department = projectsDetails.get(index);
	}

	public void deleteProject(int index) {
		projectsDetails.remove(index);
		System.out.println();
	}
}
