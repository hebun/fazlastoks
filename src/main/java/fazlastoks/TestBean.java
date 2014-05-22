package fazlastoks;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.BiConsumer;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import model.Product;
import freela.util.DoMail;
import freela.util.FaceUtils;

@ViewScoped
@ManagedBean
public class TestBean {
	Product pro;

	public TestBean() {
		System.out.println("blblasdbflsdf");
		dumpCompTree();
		try {
			HttpServletRequest request = (HttpServletRequest) FacesContext
					.getCurrentInstance().getExternalContext().getRequest();
			String contextURL = request.getRequestURL().toString()
					.replace(request.getRequestURI().substring(0), "")
					+ request.getContextPath();

			FaceUtils.log.info(contextURL);
			// DoMail.main(null);
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void dumpCompTree() {
		
		FacesContext context = FacesContext.getCurrentInstance();
		UIInput input = new UIInput();
		Map<String, Object> map = input.getAttributes();
		map.put("id", "Myid");
		map.put("value", "blblba my value");

		map.forEach( new BiConsumer<String, Object>() {
			@Override
			public void accept(String t, Object u) {
				System.out.println(t + ":" + u);
				FaceUtils.log.info(t + ":" + u);
			}
		} );

	}

	public Product getPro() {
		return pro;
	}

	public void setPro(Product pro) {
		this.pro = pro;
	}
}
