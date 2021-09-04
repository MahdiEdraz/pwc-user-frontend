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

@ManagedBean(name = "departmentController")
@SessionScoped
public class DepartmentController implements Serializable {
	private List<Department> departmentsDetails = new ArrayList();
	private String newDepartment = "";

	@PostConstruct
	public void init() {

	}

	public void onRowEdit(RowEditEvent event) {
		Department department = (Department) event.getObject();
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
		System.out.println(newDepartment);
	}

	public void updateDepartment(Department updatedDepartment, int index) {
		Department department = departmentsDetails.get(index);
	}

	public void deleteDepartment(int index) {
		departmentsDetails.remove(index);
		System.out.println();
	}

}
