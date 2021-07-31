package com.krazymood.app.configuration;/*
 * package com.krazymood.app.configuration;
 * 
 * import javax.servlet.ServletContext; import
 * javax.servlet.ServletRegistration;
 * 
 * import org.glassfish.jersey.servlet.WebConfig; import org.hibernate.Filter;
 * import org.springframework.util.Assert; import
 * org.springframework.util.ObjectUtils; import
 * org.springframework.web.context.WebApplicationContext; import
 * org.springframework.web.servlet.DispatcherServlet; import
 * org.springframework.web.servlet.support.
 * AbstractAnnotationConfigDispatcherServletInitializer;
 * 
 * public class WebAppInitializer extends
 * AbstractAnnotationConfigDispatcherServletInitializer {
 * 
 * @Override protected Class<?>[] getRootConfigClasses() { return null; }
 * 
 * @Override protected Class<?>[] getServletConfigClasses() { return new Class[]
 * { WebConfig.class }; }
 * 
 * @Override protected String[] getServletMappings() { return new String[] { "/"
 * }; }
 * 
 * @Override protected void registerDispatcherServlet(ServletContext
 * servletContext) { String servletName = getServletName();
 * Assert.hasLength(servletName,
 * "getServletName() may not return empty or null");
 * 
 * WebApplicationContext servletAppContext = createServletApplicationContext();
 * Assert.notNull(servletAppContext,
 * "createServletApplicationContext() did not return an application " +
 * "context for servlet [" + servletName + "]");
 * 
 * DispatcherServlet dispatcherServlet = new
 * DispatcherServlet(servletAppContext);
 * 
 * // throw NoHandlerFoundException to Controller
 * dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
 * 
 * ServletRegistration.Dynamic registration =
 * servletContext.addServlet(servletName, dispatcherServlet);
 * Assert.notNull(registration, "Failed to register servlet with name '" +
 * servletName + "'." +
 * "Check if there is another servlet registered under the same name.");
 * 
 * registration.setLoadOnStartup(1);
 * registration.addMapping(getServletMappings());
 * registration.setAsyncSupported(isAsyncSupported());
 * 
 * Filter[] filters = (Filter[]) getServletFilters(); if
 * (!ObjectUtils.isEmpty(filters)) { for (Filter filter : filters) {
 * registerServletFilter(servletContext, (javax.servlet.Filter) filter); } }
 * 
 * customizeRegistration(registration); } }
 */