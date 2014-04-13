package model;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import fazlastoks.FaceUtils;

@FacesConverter(forClass = model.Product.class, value = "proConverter")
public class ProductConverter implements Converter {

	private transient Session ss;

	@SuppressWarnings("unchecked")
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) throws ConverterException {

		this.ss = FaceUtils.getHibernateSession();
		System.out.println("getting for converter");
		ss.getTransaction().begin();
		List<Product> list = ss.createCriteria(Product.class)
				.add(Restrictions.eq("id", Integer.parseInt(value))).list();
		ss.getTransaction().commit();
		Product ret;
		if (list.size() > 0) {

			ret = list.get(0);
			;
		} else
			ret = null;

		System.out.println(ret);
		return ret;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) throws ConverterException {

		if (value == null)
			return null;

		Integer id = ((Product) value).getId();

		if (id == null)
			return null;

		return id.toString();
	}

}