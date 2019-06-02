package org.tomcat.startup;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.tomcat.catalina.Loader;
import org.tomcat.catalina.Mapper;
import org.tomcat.catalina.SimpleContextLifecycleListener;
import org.tomcat.catalina.Wrapper;
import org.tomcat.catalina.context.SimpleContext;
import org.tomcat.catalina.loader.SimpleLoader;
import org.tomcat.catalina.logger.FileLogger;
import org.tomcat.catalina.mapper.SimpleContextMapper;
import org.tomcat.catalina.valve.ClientIPLoggerValve;
import org.tomcat.catalina.valve.HeaderLoggerValve;
import org.tomcat.catalina.wrapper.SimpleWrapper;
import org.tomcat.connector.HttpConnector;
import org.tomcat.net.DefaultServletFactory;

import java.io.IOException;

public class Bootstrap {

    static Log log = LogFactory.getLog(Bootstrap.class);

    public static void main(String[] args) {
        log.debug("start up");
//        SimpleContainer simpleContainer = new SimpleContainer();
        SimpleContext context = new SimpleContext();
        SimpleContextLifecycleListener lifecycleListener = new SimpleContextLifecycleListener();
        context.addLifecycleListener(lifecycleListener);
        SimpleWrapper simpleWrapper = new SimpleWrapper();
        simpleWrapper.setServletClass("ModernServlet");
        simpleWrapper.setName("Modern");
        Wrapper wrapper1 = new SimpleWrapper();
        wrapper1.setName("Primitive");
        wrapper1.setServletClass("PrimitiveServlet");
        context.addChild(simpleWrapper);
        context.addChild(wrapper1);

        Loader simpleLoader = new SimpleLoader();
        context.setLoader(simpleLoader);
        context.addValve(new ClientIPLoggerValve());
        context.addValve(new HeaderLoggerValve());

        Mapper mapper = new SimpleContextMapper();
        mapper.setProtocol("http");
        context.addMapper(mapper);
        context.addServletMapping("servlet/Primitive", "Primitive");
        context.addServletMapping("servlet/Modern", "Modern");
        HttpConnector connector = new HttpConnector();
        FileLogger logger = new FileLogger();
        System.setProperty("catalina.base", System.getProperty("user.dir"));
        logger.setPrefix("FileLog_");
        logger.setSuffix(".txt");
        logger.setTimestamp(true);
        logger.setDirectory("webroot");
        context.setLogger(logger);


        connector.setContainer(context);
        connector.setFactory(new DefaultServletFactory());
        connector.initialize();
        connector.start();
        context.start();
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
