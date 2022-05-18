package vn.ptit.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "user")
public class UserInfo extends BaseEntity implements UserDetails{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "username", length = 100, nullable = false)
	private String username;
	
	@Column(name = "password", length = 100, nullable = false)
	private String password;
	
	@Column(name = "fullname", length = 100, nullable = false)
	private String fullname;
	
	@Column(name = "phonenumber", length = 100, nullable = false)
	private String phonenumber;
	
	@Column(name = "email", length = 100, nullable = false)
	private String email;
	
	@Column(name = "status", nullable = true)
	private Boolean status;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "userInfo", fetch = FetchType.LAZY)
	private List<UserAttachment> userAttachments = new ArrayList<UserAttachment>();
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<Role> roles = new ArrayList<Role>();

	public void addUserAttachment(UserAttachment userAttachment) {
		userAttachments.add(userAttachment);
		userAttachment.setUserInfo(this);
	}

	public void removeUserAttachment(UserAttachment userAttachment) {
		userAttachments.remove(userAttachment);
		userAttachment.setUserInfo(null);
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

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<UserAttachment> getUserAttachments() {
		return userAttachments;
	}

	public void setUserAttachments(List<UserAttachment> userAttachments) {
		this.userAttachments = userAttachments;
	}
	
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return (Collection<? extends GrantedAuthority>) roles;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
