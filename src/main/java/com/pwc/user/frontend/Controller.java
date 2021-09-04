package com.pwc.user.frontend;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;

import com.pwc.user.frontend.model.User;

import java.io.Serializable;
import java.util.*;

@ManagedBean(name="controller")
@SessionScoped
public class Controller implements Serializable {
	private String department ;
	private List<User> users = new ArrayList();
	private User newUser = new User();
	
	@PostConstruct
	public void init() {
		User user = new User();
		user.setFirstName("aa");
		user.setId(1);
		user.setLastName("fr");
		user.setUserType("user");
		User user2 = new User();
		user2.setFirstName("ahmad");
		user2.setId(2);
		user2.setLastName("fr");
		user2.setUserType("user");
		
		users.add(user);
		users.add(user2);	}
	
	public void onTabChange(TabChangeEvent event) {
        FacesMessage msg = new FacesMessage("Tab Changed", "Active Tab: " + event.getTab().getTitle());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onTabClose(TabCloseEvent event) {
        FacesMessage msg = new FacesMessage("Tab Closed", "Closed tab: " + event.getTab().getTitle());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    public void onRowEdit(RowEditEvent event) {
    	User user = (User) event.getObject();
        FacesMessage msg = new FacesMessage("User Edited", String.valueOf(user.getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
    }
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public List<User> getUsers() {

		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	public void createUser() {
		System.out.println(newUser);
    }
	public void updateUser(User updatedUser,int index) {
		User user =users.get(index);
        user.setFirstName(updatedUser.getFirstName());
    }
	
	public void deleteUser( int index) {
		users.remove(index);
		System.out.println();
	}

	public User getNewUser() {
		return newUser;
	}

	public void setNewUser(User newUser) {
		this.newUser = newUser;
	}
    

}
