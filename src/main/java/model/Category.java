// default package
// Generated Apr 11, 2014 10:39:23 PM by Hibernate Tools 4.0.0
package model;
import javax.persistence.Column;

public class Category implements java.io.Serializable {

	private Integer id;
	private String cname;

	public Category() {
	}

	public Category(String cname) {
		this.cname = cname;
	
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "cname")
	public String getCname() {
		return this.cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}


}
