//package com.michaelmbugua.expenseTrackerApi;
//
//import org.apache.catalina.Context;
//import org.apache.catalina.connector.Connector;
//import org.apache.tomcat.util.descriptor.web.SecurityCollection;
//import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
//import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//
////@Configuration
////public class MyConfig {
////
////        private final static String SECURITY_USER_CONSTRAINT = "CONFIDENTIAL";
////        private final static String REDIRECT_PATTERN = "/*";
////        private final static String CONNECTOR_PROTOCOL = "org.apache.coyote.http11.Http11NioProtocol";
////        private final static String CONNECTOR_SCHEME = "http";
////
////
////
////        // Redirect http to https config beans
////    @Bean
////    public TomcatServletWebServerFactory servletContainer() {
////        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
////            @Override
////            protected void postProcessContext(Context context) {
////                SecurityConstraint securityConstraint = new SecurityConstraint();
////                securityConstraint.setUserConstraint("CONFIDENTIAL");
////                SecurityCollection collection = new SecurityCollection();
////                collection.addPattern("/*");
////                securityConstraint.addCollection(collection);
////                context.addConstraint(securityConstraint);
////            }
////        };
////
////        tomcat.addAdditionalTomcatConnectors(initiateHttpConnector());
////        return tomcat;
////    }
////
////    private Connector initiateHttpConnector() {
////        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
////        connector.setScheme("http");
////        connector.setPort(8080);
////        connector.setSecure(false);
////        connector.setRedirectPort(8081);
////
////        return connector;
////    }
////
////}
//@ComponentScan
//@Configuration
//@EnableAutoConfiguration
//class MyConfig {
//
//    @Bean
//    public ServletWebServerFactory servletContainer() {
//        TomcatServletWebServerFactory tomcat = new  TomcatServletWebServerFactory() {
//            @Override
//            protected void postProcessContext(Context context) {
//                SecurityConstraint securityConstraint = new SecurityConstraint();
//                securityConstraint.setUserConstraint("CONFIDENTIAL");
//                SecurityCollection collection = new SecurityCollection();
//                collection.addPattern("/*");
//                securityConstraint.addCollection(collection);
//                context.addConstraint(securityConstraint);
//            }
//        };
//        tomcat.addAdditionalTomcatConnectors(getHttpConnector());
//        return tomcat;
//    }
//
//    private Connector getHttpConnector() {
//        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//        connector.setScheme("http");
//        connector.setPort(8444);
//        connector.setSecure(false);
//        connector.setRedirectPort(8443);
//        return connector;
//    }
//}