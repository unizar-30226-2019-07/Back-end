package main.java.usuario;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // This tells Hibernate to make a table out of this class
public class Usuario {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer idUsuario;
    private String email;
    private String status;
    private String first_name;
    private String last_name;
    private String gender;
    private String birth_date;
    private float location_lat;
    private float location_lng;
    private float rating;

	public Integer getId() {
		return idUsuario;
	}

	public void setId(Integer id) {
		this.idUsuario = idUsuario;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getBirth_date() {
		return birth_date;
	}

	public void setBirth_date(String birth_date) {
		this.birth_date = birth_date;
	}
	
	public float getLocation_lat() {
		return location_lat;
	}

	public void setLocation_lat(float location_lat) {
		this.location_lat = location_lat;
	}
	
	public float getLocation_lng() {
		return location_lng;
	}

	public void setLocation_lng(float location_lng) {
		this.location_lng = location_lng;
	}
	
	public float getRating() {
		return location_lat;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}
	
}

