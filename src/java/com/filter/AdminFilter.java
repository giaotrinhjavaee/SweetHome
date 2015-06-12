/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.filter;

import com.entity.Users;
import com.util.SessionUtils;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
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

/**
 *
 * @author admin
 */
@WebFilter(filterName = "AdminFilter", urlPatterns = {
    "/admin/*"
})
public class AdminFilter implements Filter {

    private static final boolean debug = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    public AdminFilter() {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        try {

            // check whether session variable is set
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;
            res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
            res.setHeader("Pragma", "no-cache"); // HTTP 1.0.
            res.setDateHeader("Expires", 0); // Proxies.

            HttpSession ses = req.getSession(true);
            //Users u = new Users("admin");
            //ses.setAttribute("loginAdmin", u);
            if (ses != null && ses.getAttribute("loginAdmin") != null) {
                chain.doFilter(request, response);
            } else {
                //req.("/admin/login.xhtml");
                req.getRequestDispatcher("/admin/login.xhtml").forward(req, res);
                //res.sendRedirect(req.getContextPath() + "/admin/login.xhtml");
            }

        } catch (Throwable t) {
            System.out.println(t.getMessage());
        }

    }

    public void destroy() {
    }

    public void init(FilterConfig filterConfig) {
    }

}
