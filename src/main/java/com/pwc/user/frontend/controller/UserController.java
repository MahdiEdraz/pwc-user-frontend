package com.pwc.user.frontend.controller;

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
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pwc.user.frontend.model.User;

import java.io.Serializable;
import java.util.*;

@ManagedBean(name="userController")
@SessionScoped
public class UserController implements Serializable {
	private List<User> users = new ArrayList();
	private User newUser = new User();
	private Client client = ClientBuilder.newClient();
	private GsonBuilder builder = new GsonBuilder(); 
    private Gson gson = builder.create() ;

	@PostConstruct
	public void init()  {
		 Response response = client
		    		           .target("http://localhost:8080/pwc-user-api/rest/user/getAllUsers")
		    		           .request(MediaType.APPLICATION_JSON)
		    		           .get();
		 
		String json = response.readEntity(String.class);
		users = gson.fromJson(json, users.getClass());
		
				
			}
	
	public void onTabChange(TabChangeEvent event) {
        FacesMessage msg = new FacesMessage("Tab Changed", "Active Tab: " + event.getTab().getTitle());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onTabClose(TabCloseEvent event) {
        FacesMessage msg = new FacesMessage("Tab Closed", "Closed tab: " + event.getTab().getTitle());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    public void onRowEdit(RowEditEvent<User> event)  {
    	User user = gson.fromJson(String.valueOf(event.getObject()), User.class);
    	updateUser(user);
        FacesMessage msg = new FacesMessage("User Edited", String.valueOf(user.getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

	public List<User> getUsers() {

		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	public void createUser()  {
		
		WebTarget target = client.target("http://localhost:8080/pwc-user-api/rest/user/createUser");
        Form form = new Form();
        String userJson = gson.toJson(newUser);
        form.param("userJson", userJson);
        String responseRes = target
        		.request(MediaType.APPLICATION_JSON)
        		.post(Entity.entity(form, MediaType.APPLICATION_JSON),String.class);
        updateMsg(responseRes);
    }
	public void updateUser(User updatedUser)  {
		WebTarget target = client.target("http://localhost:8080/pwc-user-api/rest/user/updateUser");
        Form form = new Form();
        String userJson = gson.toJson(updatedUser);
        form.param("userJson", userJson);
        String responseRes = target
        		.request(MediaType.APPLICATION_JSON)
        		.put(Entity.entity(form, MediaType.APPLICATION_JSON),String.class);
    }
	
	public void deleteUser( int index) {
		WebTarget target = client.target("http://localhost:8080/pwc-user-api/rest/user/deleteUser");
        Form form = new Form();
        String userJson = gson.toJson(users.get(index));
        form.param("userJson", userJson);
        String responseRes = target
        		.request(MediaType.APPLICATION_JSON)
        		.post(Entity.entity(form, MediaType.APPLICATION_JSON),String.class);
		users.remove(index);
	}

	public User getNewUser() {
		return newUser;
	}

	public void setNewUser(User newUser) {
		this.newUser = newUser;
	}
    
     public void updateMsg(String messsag) {
    	  FacesMessage msg = new FacesMessage(messsag);
          FacesContext.getCurrentInstance().addMessage(null, msg);
     }
}
