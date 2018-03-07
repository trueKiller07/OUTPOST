package com.MMI.OUTPOST.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

/**
 *
 * @author Deepak Jain & Sameer Sharma
 */

@Document(collection = "users")
public class Users implements Serializable {

   @Id
   private BigInteger sno;
	
    private String projectname;
	
	private String password;
	
	private String phone;
	
	private String email;
	
    @Field(value="is_active")
    private   short isactive;
    
    @DateTimeFormat(iso = ISO.DATE_TIME)
    @Field(value="created_on")
    private Date createdon;
    
    @Field(value="client_secret")
    private String clientsecret;
    
    @Indexed
    @Field(value="client_id")
    private String clientId;
    
    private String org;
    
    @Field(value="off_email")
    private String offemail;
    
    private String role;
    
    private String scope;
    
    private int ttl;
    
    public Users() {}
        

    public BigInteger getSno() {
		return sno;
	}


	public void setSno(BigInteger sno) {
		this.sno = sno;
	}


	public Users(String projectname, String password, String phone, String email, short isactive, Date createdon,
			String clientsecret, String clientId, String org, String offemail, String role, String scope,int ttl) {
		super();
		this.projectname = projectname;
		this.password = password;
		this.phone = phone;
		this.email = email;
		this.isactive = isactive;
		this.createdon = createdon;
		this.clientsecret = clientsecret;
		this.clientId = clientId;
		this.org = org;
		this.offemail = offemail;
		this.role = role;
		this.scope = scope;
		this.ttl = ttl;
	}


	public String getProjectname() {
		return projectname;
	}




	public int getTtl() {
		return ttl;
	}


	public void setTtl(int ttl) {
		this.ttl = ttl;
	}


	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}




	public String getPassword() {
		return password;
	}




	public void setPassword(String password) {
		this.password = password;
	}




	public String getPhone() {
		return phone;
	}




	public void setPhone(String phone) {
		this.phone = phone;
	}




	public String getEmail() {
		return email;
	}




	public void setEmail(String email) {
		this.email = email;
	}




	public short getIsactive() {
		return isactive;
	}




	public void setIsactive(short isactive) {
		this.isactive = isactive;
	}




	public String getClientsecret() {
		return clientsecret;
	}




	public void setClientsecret(String clientsecret) {
		this.clientsecret = clientsecret;
	}




	public String getClientId() {
		return clientId;
	}




	public void setClientId(String clientId) {
		this.clientId = clientId;
	}




	public String getOrg() {
		return org;
	}




	public void setOrg(String org) {
		this.org = org;
	}




	public String getOffemail() {
		return offemail;
	}




	public void setOffemail(String offemail) {
		this.offemail = offemail;
	}




	public String getRole() {
		return role;
	}




	public void setRole(String role) {
		this.role = role;
	}




	public String getScope() {
		return scope;
	}




	public void setScope(String scope) {
		this.scope = scope;
	}

	
	public Date getCreatedon() {
		return createdon;
	}


	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}


	@Override
    public String toString() {
        return "Users{" + "username=" + projectname + ", password=" + password + ", client_id=" + clientId + ", client_secret=" + clientsecret + ", created_on=" + createdon + ", is_active=" + isactive + ", email=" + email + ", phone=" + phone + ", org=" + org + ", off_email=" + offemail + ", role=" + role + ", scope=" + scope + '}';
    }
}
