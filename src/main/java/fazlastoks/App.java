package fazlastoks;
import java.io.Serializable;
import java.util.Map;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean(name = "app", eager = true)
@ApplicationScoped
public class App implements Serializable {

	int callerCount = 0;
	public String siteUrl="";
	public Map parameters;
	
	public App() {
		final FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext ctx = facesContext.getExternalContext();
        parameters = ctx.getInitParameterMap();
        
        
	}



	private void out(String in) {
		System.out.println(in);
	}
}