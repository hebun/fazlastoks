// Generated By db to  class
package model;

import freela.util.FaceUtils;

public class User implements java.io.Serializable {

	public User() {
	}

	private int id;
	private String email;
	private String password;
	private String firmaname;
	private String uname;
	private String cepno;
	private String sabitno;
	private String address;
	private String city;
	private String website;
	private String vergidaire;
	private String vergino;
	private Object productCount;

	public Object getProductCount() {
		return productCount;
	}

	public void setProductCount(Object productCount) {
		FaceUtils.log.info(productCount+"");
		this.productCount = productCount;
	}

	public int getId() {
		return id;
	}

	public void setId(int value) {
		id = value;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String value) {
		email = value;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String value) {
		password = value;
	}

	public String getFirmaname() {
		return firmaname;
	}

	public void setFirmaname(String value) {
		firmaname = value;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String value) {
		uname = value;
	}

	public String getCepno() {
		return cepno;
	}

	public void setCepno(String value) {
		cepno = value;
	}

	public String getSabitno() {
		return sabitno;
	}

	public void setSabitno(String value) {
		sabitno = value;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String value) {
		address = value;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String value) {
		city = value;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String value) {
		website = value;
	}

	public String getVergidaire() {
		return vergidaire;
	}

	public void setVergidaire(String value) {
		vergidaire = value;
	}

	public String getVergino() {
		return vergino;
	}

	public void setVergino(String value) {
		vergino = value;
	}
}