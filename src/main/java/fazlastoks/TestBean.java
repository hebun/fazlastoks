package fazlastoks;

import static freela.util.FaceUtils.log;

import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import freela.util.DoMail;
import model.Product;

@ViewScoped
@ManagedBean
public class TestBean {
	Product pro;

	public TestBean() {
	
		try {
			DoMail.main(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	public void dumpCompTree() {
		FacesContext context = FacesContext.getCurrentInstance();
		UIInput input = new UIInput();
		Map<String, Object> map = input.getAttributes();
		map.put("id", "Myid");
		map.put("value", "blblba my value");

		System.out.println(context.getExternalContext()
				.getRequestParameterMap().toString());

	}

	public Product getPro() {
		return pro;
	}

	public void setPro(Product pro) {
		this.pro = pro;
	}
}
