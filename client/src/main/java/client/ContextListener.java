package client;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setInitParameter("javax.faces.ENABLE_WEBSOCKET_ENDPOINT", "true");
        sce.getServletContext().setInitParameter("primefaces.THEME", "cruze");
    }
}
