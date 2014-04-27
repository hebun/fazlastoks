package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import freela.util.Db;
import freela.util.Sql;
import freela.util.Db.SelectCallback;
import freela.util.Sql.Select;

@FacesConverter(forClass = model.Product.class, value = "proConverter")
public class ProductConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) throws ConverterException {

		String select = new Select().from("product").where("id=", value).get();
		final Product p = new Product();
		Db.select(select, new SelectCallback() {

			@Override
			public void callback(ResultSet rs, String[] columns)
					throws SQLException {

				while (rs.next()) {
					
					p.setId(rs.getInt("id"));
					p.setPname(rs.getString("pname"));
					p.setContent(rs.getString("content"));
					p.setPrice(rs.getInt("price"));
					p.setExpiredate(rs.getDate("expiredate"));
					p.setPstate(rs.getString("pstate"));
					p.setQuantity(rs.getString("quantity"));
					
					
				}
			}
		});
		return  p;
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