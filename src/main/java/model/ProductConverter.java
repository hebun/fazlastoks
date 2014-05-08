package model;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import freela.util.FaceUtils;

@FacesConverter(forClass = model.Product.class, value = "proConv")
public class ProductConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) throws ConverterException {

		Product p = FaceUtils.getObjectById(Product.class, "product", value);
		return p;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) throws ConverterException {

		FaceUtils.log.info(value.toString());

		if (value == null)
			return null;

		Integer id = ((Product) value).getId();

		if (id == null)
			return null;

		return id.toString();
	}

}