/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/10.1.36
 * Generated at: 2025-04-02 07:07:53 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.jsp.*;
import java.util.HashMap;
import java.util.Map;

public final class register_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports,
                 org.apache.jasper.runtime.JspSourceDirectives {

  private static final jakarta.servlet.jsp.JspFactory _jspxFactory =
          jakarta.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.LinkedHashSet<>(4);
    _jspx_imports_packages.add("jakarta.servlet");
    _jspx_imports_packages.add("jakarta.servlet.http");
    _jspx_imports_packages.add("jakarta.servlet.jsp");
    _jspx_imports_classes = new java.util.LinkedHashSet<>(3);
    _jspx_imports_classes.add("java.util.Map");
    _jspx_imports_classes.add("java.util.HashMap");
  }

  private volatile jakarta.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public boolean getErrorOnELNotFound() {
    return false;
  }

  public jakarta.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final jakarta.servlet.http.HttpServletRequest request, final jakarta.servlet.http.HttpServletResponse response)
      throws java.io.IOException, jakarta.servlet.ServletException {

    if (!jakarta.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      final java.lang.String _jspx_method = request.getMethod();
      if ("OPTIONS".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        return;
      }
      if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET, POST or HEAD. Jasper also permits OPTIONS");
        return;
      }
    }

    final jakarta.servlet.jsp.PageContext pageContext;
    jakarta.servlet.http.HttpSession session = null;
    final jakarta.servlet.ServletContext application;
    final jakarta.servlet.ServletConfig config;
    jakarta.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    jakarta.servlet.jsp.JspWriter _jspx_out = null;
    jakarta.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html lang=\"en\">\n");
      out.write("<head>\n");
      out.write("    <meta charset=\"UTF-8\">\n");
      out.write("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
      out.write("    <title>Register - IoT Bay</title>\n");
      out.write("    <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css\">\n");
      out.write("    <link rel=\"stylesheet\" href=\"styles.css\">\n");
      out.write("</head>\n");
      out.write("<body>\n");
      out.write("<header>\n");
      out.write("    <div class=\"container\">\n");
      out.write("        <nav>\n");
      out.write("            <a href=\"index.jsp\" class=\"logo\">\n");
      out.write("                <i class=\"fas fa-bolt\"></i> IoT Bay\n");
      out.write("            </a>\n");
      out.write("            <div class=\"nav-links\">\n");
      out.write("                <a href=\"index.jsp\">Home</a>\n");
      out.write("                <a href=\"login.jsp\">Login</a>\n");
      out.write("                <a href=\"register.jsp\">Register</a>\n");
      out.write("                <a href=\"logout.jsp\">Logout</a>\n");
      out.write("            </div>\n");
      out.write("        </nav>\n");
      out.write("    </div>\n");
      out.write("</header>\n");
      out.write("\n");
      out.write("<div class=\"register-container\">\n");
      out.write("    <div class=\"register-form\">\n");
      out.write("        <h2>Create Your Account</h2>\n");
      out.write("\n");
      out.write("        ");
      out.write("\n");
      out.write("        ");

            if ("POST".equalsIgnoreCase(request.getMethod())) {
                String email = request.getParameter("email");
                String password = request.getParameter("password");
                String confirmPassword = request.getParameter("confirmPassword");

                // Simple validation
                if (!password.equals(confirmPassword)) {
                    request.setAttribute("error", "Passwords do not match!");
                } else {
                    // Get or create user database in application scope
                    Map<String, String> userDatabase = new HashMap<>();
                    if (application.getAttribute("userDatabase") != null) {
                        userDatabase = (Map<String, String>)application.getAttribute("userDatabase");
                    }

                    // Add new user
                    userDatabase.put(email, password);
                    application.setAttribute("userDatabase", userDatabase);

                    // Auto-login after registration
                    session.setAttribute("userEmail", email);
                    response.sendRedirect("index.jsp");
                }
            }
        
      out.write("\n");
      out.write("\n");
      out.write("        ");
      out.write("\n");
      out.write("        ");
 if (request.getAttribute("error") != null) { 
      out.write("\n");
      out.write("        <div class=\"error-message\">\n");
      out.write("            <i class=\"fas fa-exclamation-circle\"></i> ");
      out.print( request.getAttribute("error") );
      out.write("\n");
      out.write("        </div>\n");
      out.write("        ");
 } 
      out.write("\n");
      out.write("\n");
      out.write("        <form method=\"post\">\n");
      out.write("            <div class=\"form-row\">\n");
      out.write("                <div class=\"input-group\">\n");
      out.write("                    <label for=\"firstName\">First Name</label>\n");
      out.write("                    <input type=\"text\" id=\"firstName\" name=\"firstName\" required>\n");
      out.write("                </div>\n");
      out.write("                <div class=\"input-group\">\n");
      out.write("                    <label for=\"lastName\">Last Name</label>\n");
      out.write("                    <input type=\"text\" id=\"lastName\" name=\"lastName\" required>\n");
      out.write("                </div>\n");
      out.write("            </div>\n");
      out.write("\n");
      out.write("            <div class=\"input-group\">\n");
      out.write("                <label for=\"email\">Email</label>\n");
      out.write("                <input type=\"email\" id=\"email\" name=\"email\" required>\n");
      out.write("            </div>\n");
      out.write("\n");
      out.write("            <div class=\"form-row\">\n");
      out.write("                <div class=\"input-group password-container\">\n");
      out.write("                    <label for=\"password\">Password</label>\n");
      out.write("                    <input type=\"password\" id=\"password\" name=\"password\" required>\n");
      out.write("                    <span class=\"toggle-password\" onclick=\"togglePassword('password')\">\n");
      out.write("                            <i class=\"fas fa-eye\"></i>\n");
      out.write("                        </span>\n");
      out.write("                </div>\n");
      out.write("                <div class=\"input-group password-container\">\n");
      out.write("                    <label for=\"confirmPassword\">Confirm Password</label>\n");
      out.write("                    <input type=\"password\" id=\"confirmPassword\" name=\"confirmPassword\" required>\n");
      out.write("                    <span class=\"toggle-password\" onclick=\"togglePassword('confirmPassword')\">\n");
      out.write("                            <i class=\"fas fa-eye\"></i>\n");
      out.write("                        </span>\n");
      out.write("                </div>\n");
      out.write("            </div>\n");
      out.write("\n");
      out.write("            <div class=\"input-group\">\n");
      out.write("                <label for=\"phone\">Phone Number</label>\n");
      out.write("                <input type=\"tel\" id=\"phone\" name=\"phone\" required>\n");
      out.write("            </div>\n");
      out.write("\n");
      out.write("            <button type=\"submit\" class=\"btn btn-primary\">\n");
      out.write("                <i class=\"fas fa-user-plus\"></i> Register\n");
      out.write("            </button>\n");
      out.write("        </form>\n");
      out.write("\n");
      out.write("        <div class=\"form-footer\">\n");
      out.write("            <p>Already have an account? <a href=\"login.jsp\">Login here</a></p>\n");
      out.write("        </div>\n");
      out.write("    </div>\n");
      out.write("</div>\n");
      out.write("\n");
      out.write("<script>\n");
      out.write("    // Toggle password visibility\n");
      out.write("    function togglePassword(fieldId) {\n");
      out.write("        const field = document.getElementById(fieldId);\n");
      out.write("        const icon = document.querySelector(`[onclick=\"togglePassword('${fieldId}')] i`);\n");
      out.write("\n");
      out.write("        if (field.type === \"password\") {\n");
      out.write("            field.type = \"text\";\n");
      out.write("            icon.classList.remove(\"fa-eye\");\n");
      out.write("            icon.classList.add(\"fa-eye-slash\");\n");
      out.write("        } else {\n");
      out.write("            field.type = \"password\";\n");
      out.write("            icon.classList.remove(\"fa-eye-slash\");\n");
      out.write("            icon.classList.add(\"fa-eye\");\n");
      out.write("        }\n");
      out.write("    }\n");
      out.write("</script>\n");
      out.write("</body>\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof jakarta.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
