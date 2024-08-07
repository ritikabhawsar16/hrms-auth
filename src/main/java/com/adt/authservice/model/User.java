package com.adt.authservice.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Proxy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.adt.authservice.model.audit.DateAudit;
import com.adt.authservice.validation.annotation.NullOrNotBlank;

@Table(catalog = "EmployeeDB", schema = "user_schema", name = "_EMPLOYEE")
@Entity(name = "_EMPLOYEE")
@Proxy(lazy = false)
public class User extends DateAudit implements UserDetails {

	@Id
	@Column(name = "EMPLOYEE_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
	@SequenceGenerator(name = "user_seq", allocationSize = 1, schema = "user_schema")
	private Long id;

	@NaturalId
	@Column(name = "EMAIL", unique = true)
	@NotBlank(message = "User email cannot be Null")
	private String email;

	@Column(name = "USERNAME", unique = true)
	@NullOrNotBlank(message = "Username can not be Blank")
	private String username;

	@Column(name = "PASSWORD")
	@NotNull(message = "Password cannot be Null")
	private String password;

	@Column(name = "FIRST_NAME")
	@NullOrNotBlank(message = "First name can not be Blank")
	private String firstName;

	@Column(name = "LAST_NAME")
	@NullOrNotBlank(message = "Last name can not be Blank")
	private String lastName;

	@Column(name = "IS_ACTIVE", nullable = false)
	private Boolean active;

	@Column(name = "ADT_ID", nullable = false, unique = true)
	private String adtId;

	@Column(name = "employee_type")
	private String employeeType;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE })
	@JoinTable(name = "USER_AUTHORITY", schema = "user_schema", joinColumns = {
			@JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "EMPLOYEE_ID") }, inverseJoinColumns = {
					@JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID") })
	private Set<Role> roles = new HashSet<>();

	@Column(name = "IS_EMAIL_VERIFIED", nullable = false)
	private Boolean isEmailVerified;

	@Column(name = "MIDDLE_NAME")
	private String middleName;

	@Column(name = "CONFIRM_PASSWORD", nullable = false)
	private String confirmPassword;

	@Transient
	private String message;

	public String getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}

	public User() {
		super();
	}

	public User(User user) {
		if (user != null) {
			id = user.getId();
			username = user.getUsername();
			password = user.getPassword();
			firstName = user.getFirstName();
			middleName = user.getMiddleName();
			lastName = user.getLastName();
			email = user.getEmail();
			confirmPassword = user.getConfirmPassword();
			active = user.getActive();
			roles = user.getRoles();
			isEmailVerified = user.getEmailVerified();
			adtId = user.getAdtId();
			employeeType = user.getEmployeeType();

		}
	}

	public void addRole(Role role) {
		roles.add(role);
		role.getUserList().add(this);
	}

	public void addRoles(Set<Role> roles) {
		roles.forEach(this::addRole);
	}

	public void removeRole(Role role) {
		roles.remove(role);
		role.getUserList().remove(this);
	}

	public void markVerificationConfirmed() {
		setEmailVerified(true);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> authorities) {
		roles = authorities;
	}

	public Boolean getEmailVerified() {
		return isEmailVerified;
	}

	public void setEmailVerified(Boolean emailVerified) {
		isEmailVerified = emailVerified;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getAdtId() {
		return adtId;
	}

	public void setAdtId(String adtId) {
		this.adtId = adtId;
	}

//	@Override
//	public String toString() {
//		return "User [id=" + id + ", email=" + email + ", username=" + username + ", password=" + password
//				+ ", firstName=" + firstName + ", lastName=" + lastName + ", active=" + active + ", adtId=" + adtId
//				+ ", roles=" + roles + ", isEmailVerified=" + isEmailVerified + ", middleName=" + middleName
//				+ ", confirmPassword=" + confirmPassword + ", message=" + message + "]";
//	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", username=" + username + ", password=" + password
				+ ", firstName=" + firstName + ", lastName=" + lastName + ", active=" + active + ", adtId=" + adtId
				+ ", employeeType=" + employeeType + ", roles=" + roles + ", isEmailVerified=" + isEmailVerified
				+ ", middleName=" + middleName + ", confirmPassword=" + confirmPassword + ", message=" + message + "]";
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}
}