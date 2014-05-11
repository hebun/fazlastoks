// default package
// Generated Apr 11, 2014 10:39:23 PM by Hibernate Tools 4.0.0
package model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

public class Productphoto implements java.io.Serializable {

	private Integer id=new Integer(0);
	private Integer productid;
	private String file;

	public Productphoto() {
	}

	public Integer getProductid() {
		return productid;
	}

	public void setProductid(Integer productid) {
		this.productid = productid;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	@Column(name = "file")
	public String getFile() {
		return this.file;
	}

	public void setFile(String file) {
		this.file = file;
	}

}
