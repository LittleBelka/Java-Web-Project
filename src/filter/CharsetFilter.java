package filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * This is the class that sets the encoding.
 */
public class CharsetFilter implements Filter {

    private String encoding;
    private ServletContext context;

    /**
     * This is the method used by the initialization parameter.
     * @param fConfig a filter configuration object
     * @throws ServletException if the request can not be handled
     */
    @Override
    public void init(FilterConfig fConfig) throws ServletException {

        encoding = fConfig.getInitParameter("characterEncoding");
        context = fConfig.getServletContext();
    }

    /**
     * This method intercepts and processes the requests to the servlet.
     * @param request servlet request
     * @param response servlet response
     * @param chain object to provide a filter chain
     * @throws IOException if an input or output error was detected
     * @throws ServletException if the request can not be handled
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        request.setCharacterEncoding(encoding);
        response.setCharacterEncoding(encoding);
        context.log("Charset was set.");
        chain.doFilter(request, response);
    }
}
