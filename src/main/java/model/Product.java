// default package
// Generated Apr 11, 2014 10:39:23 PM by Hibernate Tools 4.0.0
package model;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


public class Product implements java.io.Serializable {

	private Integer id=new Integer(0);
	private String pname;
	private String content;
	private String pstate;
	private String quantity;
	private Integer price;
	private Date expiredate;
	private String keywords;
	private String file;
	private Set<Productphoto> productphotos = new HashSet<Productphoto>(0);
	private Set<Productcategory> productcategories = new HashSet<Productcategory>(
			0);

	public Product() {
	}

	public Product(String pname, String content, String pstate,
			String quantity, Integer price, Date expiredate, String keywords,
			String file, Set<Productphoto> productphotos,
			Set<Productcategory> productcategories) {
		this.pname = pname;
		this.content = content;
		this.pstate = pstate;
		this.quantity = quantity;
		this.price = price;
		this.expiredate = expiredate;
		this.keywords = keywords;
		this.file = file;
		this.productphotos = productphotos;
		this.productcategories = productcategories;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPname() {
		return this.pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}


	public String getPstate() {
		return this.pstate;
	}

	public void setPstate(String pstate) {
		this.pstate = pstate;
	}

	public String getQuantity() {
		return this.quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public Integer getPrice() {
		return this.price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Date getExpiredate() {
		return this.expiredate;
	}

	public void setExpiredate(Date expiredate) {
		this.expiredate = expiredate;
	}

	public String getKeywords() {
		return this.keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getFile() {
		return this.file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public Set<Productphoto> getProductphotos() {
		return this.productphotos;
	}

	public void setProductphotos(Set<Productphoto> productphotos) {
		this.productphotos = productphotos;
	}

	public Set<Productcategory> getProductcategories() {
		return this.productcategories;
	}

	public void setProductcategories(Set<Productcategory> productcategories) {
		this.productcategories = productcategories;
	}

}
