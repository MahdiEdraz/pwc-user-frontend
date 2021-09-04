package com.pwc.user.frontend.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.primefaces.event.RowEditEvent;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pwc.user.frontend.model.Department;
import com.pwc.user.frontend.model.Project;

@ManagedBean(name = "projectController")
@SessionScoped
public class ProjectController  implements Serializable{
	private List<Project> projectsDetails = new ArrayList();
	private String newProject = "";
	private Client client = ClientBuilder.newClient();
	private GsonBuilder builder = new GsonBuilder(); 
    private Gson gson = builder.create() ;
    private Project project = new Project();

	@PostConstruct
	public void init() {
		 Response response = client
		           .target("http://localhost:8080/pwc-user-api/rest/project/getProjectsDetails")
		           .request(MediaType.APPLICATION_JSON)
		           .get();

      String json = response.readEntity(String.class);
      projectsDetails = gson.fromJson(json, projectsDetails.getClass());
	}

	public void onRowEdit(RowEditEvent event) {
		Project project = gson.fromJson(String.valueOf(event.getObject()), Project.class);
        updateProject(project);
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
	
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public void createProject() {
		WebTarget target = client.target("http://localhost:8080/pwc-user-api/rest/project/assignUserToProject");
        Form form = new Form();
        String userJson = gson.toJson(project);
        form.param("json", userJson);
        String responseRes = target
        		.request(MediaType.APPLICATION_JSON)
        		.post(Entity.entity(form, MediaType.APPLICATION_JSON),String.class);
	}

	public void updateProject(Project project) {

		WebTarget target = client.target("http://localhost:8080/pwc-user-api/rest/project/updateUserProject");
        Form form = new Form();
        String json = gson.toJson(project);
        form.param("json", json);
        String responseRes = target
        		.request(MediaType.APPLICATION_JSON)
        		.put(Entity.entity(form, MediaType.APPLICATION_JSON),String.class);
	}

	public void deleteProject(int index) {
		WebTarget target = client.target("http://localhost:8080/pwc-user-api/rest/project/deleteUserOnProject");
        Form form = new Form();
        String json = gson.toJson(projectsDetails.get(index));
        form.param("json", json);
        String responseRes = target
        		.request(MediaType.APPLICATION_JSON)
        		.post(Entity.entity(form, MediaType.APPLICATION_JSON),String.class);
        projectsDetails.remove(index);
	}
}
