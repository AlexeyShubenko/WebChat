package com.course.init;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * В Tomcat-е есть контейнер сервлетов под названием Catalina. Не спринговых. Он ничего про спринг не знает и не понимает.
 * Контейнеров есть много разных. Каждый реагирует на свои аннотации и классы.
 * При запуске Tomcat запускается контейнер сервлетов Catalina, она лезет в папку webapps и все в ней сканирует (xml-и если они есть)
 * и ищет все классы, которые наследуют от HttpServlet, загружает их в permament generation и создает объекты этих классов.
 * Анализирует аннотации @WebServlet или смотрит в файл web.xml, где описываются сервлеты и их маппинг.
 * Catalina реагирует на свои аннотации @WebServlet, @WebListener и т.д. (о Spring аннотациях она ничего не знает и на них никак не реагирует)
 *
 * Если есть у класса наследника HttpServlet метод init(), происходит автоматический вызов этого метода в сервлете.
 * В Spring есть только один сервлет DispatcherServlet наследник HttpServlet-а. В нем есть метод init(), в котором условно говоря
 * запускается Spring контейнер.
 * После запуска Spring контейнера, контейнер ищет класс наследник от WebApplicationInitializer.
 * Находит его, загружает в permament generation и создает объект этого класса.
 * Если такого класса не было бы, то Catalina смотрит в web.xml
 *
 * Т.е. в нашем случае после поднятия Spring контейнера, контейнер запустит метод onStartup(), где
 * произойдет регистрация DispatcherServlet и мэппинг куда посылать запросы
 *
 * Далее Spring контейнер сканирует папку с конфигурационной информацией коннекта к БД и т.п., т.е. классы помеченные аннотацией @Configuration
 *
 */
public class WebAppInitializer implements WebApplicationInitializer{

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        WebApplicationContext context = getContext();

        // для работы аспектов, интерсепторов, исключений и др. необходим слушатель событий ContextLoaderListener
        servletContext.addListener(new ContextLoaderListener(context));

        ServletRegistration.Dynamic dispatcher = servletContext
                .addServlet("DispatcherServlet", new DispatcherServlet(context));

        dispatcher.setLoadOnStartup(1);
        // Куда меппим DispatcherServlet
        dispatcher.addMapping("/");
    }

    private AnnotationConfigWebApplicationContext getContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        ///вся інша конфігураційна інформація знаходиться в config
        context.setConfigLocation("com.course.config");
        return context;
    }

}
