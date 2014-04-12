package fazlastoks;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "AuthFilter", urlPatterns = { "*" })
public class AuthFilter implements Filter {

	public AuthFilter() {
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try {

			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse res = (HttpServletResponse) response;
			HttpSession ses = req.getSession(false);

			String reqURI = req.getRequestURI();
			
			if (adminControle(req, res, ses, reqURI)) {
				
				if (memberControle(req, res, ses, reqURI)) {
					
					chain.doFilter(request, response);
				}
			}

		} catch (Throwable t) {
			FaceUtils.log.info(t.getMessage());
			t.printStackTrace();
		}
	}

	private boolean memberControle(HttpServletRequest req,
			HttpServletResponse res, HttpSession ses, String reqURI)
			throws IOException {

		String[] memberPages = { "urunlerim", "urun" };

		for (String string : memberPages) {

			if (reqURI.indexOf(string) >= 0) {
				if (ses != null && ses.getAttribute("username") != null ||true) {
				} else {

					res.sendRedirect(req.getContextPath() + "/login");
					return false;
				}
			}
		}
		return true;

	}

	private boolean adminControle(HttpServletRequest req,
			HttpServletResponse res, HttpSession ses, String reqURI)
			throws IOException {
		/**
		 * admin access only
		 */
		if (reqURI.indexOf("/admin/") >= 0) {

			if (ses == null || ses.getAttribute("username") == null
					|| !ses.getAttribute("status").toString().equals("ADMIN")) {
				FaceUtils.log.info("not authorized attempt to access admin/");

				res.sendRedirect(req.getContextPath() + "/login");
				return false;
			}

		}
		return true;
	}

	@Override
	public void destroy() {

	}
}