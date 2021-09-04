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
import com.pwc.user.frontend.model.User;

@ManagedBean(name = "departmentController")
@SessionScoped
public class DepartmentController implements Serializable {
	private List<Department> departmentsDetails = new ArrayList();
	private String newDepartment = "";
	private Client client = ClientBuilder.newClient();
	private GsonBuilder builder = new GsonBuilder(); 
    private Gson gson = builder.create() ;
    private Department department = new Department();
	@PostConstruct
	public void init() {
		 Response response = client
		           .target("http://localhost:8080/pwc-user-api/rest/department/getDepartmentsDetails")
		           .request(MediaType.APPLICATION_JSON)
		           .get();

        String json = response.readEntity(String.class);
        departmentsDetails = gson.fromJson(json, departmentsDetails.getClass());
	}

	public void onRowEdit(RowEditEvent<Department> event) {
		Department department = gson.fromJson(String.valueOf(event.getObject()), Department.class);
        updateDepartment(department);
		FacesMessage msg = new FacesMessage("User Edited", String.valueOf(department.getId()));
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public List<Department> getDepartmentsDetails() {
		return departmentsDetails;
	}

	public void setDepartmentsDetails(List<Department> departmentsDetails) {
		this.departmentsDetails = departmentsDetails;
	}

	public String getNewDepartment() {
		return newDepartment;
	}

	public void setNewDepartment(String newDpartment) {
		this.newDepartment = newDpartment;
	}

	public void createDepartment() {
		WebTarget target = client.target("http://localhost:8080/pwc-user-api/rest/department/assignUserToDepartment");
        Form form = new Form();
        String userJson = gson.toJson(department);
        form.param("json", userJson);
        String responseRes = target
        		.request(MediaType.APPLICATION_JSON)
        		.post(Entity.entity(form, MediaType.APPLICATION_JSON),String.class);
	}

	public void updateDepartment(Department updatedDepartment) {
		
		WebTarget target = client.target("http://localhost:8080/pwc-user-api/rest/department/updateUserDepartment");
        Form form = new Form();
        String userJson = gson.toJson(updatedDepartment);
        form.param("json", userJson);
        String responseRes = target
        		.request(MediaType.APPLICATION_JSON)
        		.put(Entity.entity(form, MediaType.APPLICATION_JSON),String.class);
	}

	public void deleteDepartment(int index) {
		WebTarget target = client.target("http://localhost:8080/pwc-user-api/rest/department/deleteUserOnProject");
        Form form = new Form();
        String userJson = gson.toJson(departmentsDetails.get(index));
        form.param("json", userJson);
        String responseRes = target
        		.request(MediaType.APPLICATION_JSON)
        		.post(Entity.entity(form, MediaType.APPLICATION_JSON),String.class);
		departmentsDetails.remove(index);
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
	
	

}
