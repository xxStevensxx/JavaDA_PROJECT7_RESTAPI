package com.nnk.springboot.domain;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Entity
@Table(name = "users")
public class User {
	
	
	public User() {}
	
    public User(String username, String password, String fullname, String role) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.role = role;
    }
	
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "Username is mandatory")
    private String username;
    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, max = 256)
    @Pattern(regexp = "(.*)([A-Z]+)(.*)", message = "The password must have at least one upper case")
    @Pattern(regexp = "(.*)([\\W]+)(.*)", message = "The password must have at least one symbol")
	// @Pattern(regexp = "?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}", message = "Password must have at least one capital letter, 8 characters one number and one symbol")
    private String password;
    @NotBlank(message = "FullName is mandatory")
    private String fullname;
    @NotBlank(message = "Role is mandatory")
    private String role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
