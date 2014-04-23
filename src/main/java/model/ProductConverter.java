package model;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = model.Product.class, value = "proConverter")
public class ProductConverter implements Converter {

	@SuppressWarnings("unchecked")
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) throws ConverterException {

		
		return new Object();
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