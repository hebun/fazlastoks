package fazlastoks;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import freela.util.Db;
import freela.util.FaceUtils;
import freela.util.Sql;
import model.Product;

@ViewScoped
@ManagedBean
public class Pro implements Serializable {

	private Product pro;

	public Pro() {
		System.out.println("pro constructor");

	}

	public String save() {
		FaceUtils.log.info("save called pro.id:" + pro.getId());

		if (!validateInput())
			return null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("Y-m-d");
		String dat = dateFormat.format(pro.getExpiredate());
		Sql sql = null;
		
	
		
		if (pro.getId()<= 0) {
			sql = new Sql.Insert("product").add("pname", pro.getPname())
					.add("content", pro.getContent()).add("expiredate", dat);
		} else {
			sql = new Sql.Update("product").add("pname", pro.getPname())
					.add("content", pro.getContent()).add("expiredate", dat)
					.where("id=", pro.getId());
		}

		Db.insert(sql.get());

		return "urunlerim";

	}

	public boolean validateInput() {

		if (pro.getExpiredate().compareTo(new Date()) <= 0) {
			System.out.println(pro.getExpiredate().toString() + "---"
					+ new Date().toString());
			String msg = "Lütfen ileri bir tarih seçin!";
			FaceUtils.addError(msg);

			return false;
		}
		return true;
	}

	public void preRenderView() {
		if (pro == null) {
			pro = new Product();
		}
	}

	private static final long serialVersionUID = -2018425860394584424L;

	public Product getPro() {
		return pro;
	}

	public void setPro(Product pro) {
		this.pro = pro;
	}
}