package com.alphasystem.bootfx.starter.application;

import javafx.application.Application;
import javafx.application.Preloader;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.alphasystem.morphologicalanalysis.ui.application.AppPreloader;
import com.sun.javafx.application.LauncherImpl;

/**
 * @author sali
 */
public abstract class AbstractJavaFxApplicationSupport extends Application {

    private static String[] savedArgs;
    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() throws Exception {
        applicationContext = SpringApplication.run(getClass(), savedArgs);
        applicationContext.getAutowireCapableBeanFactory().autowireBean(this);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        applicationContext.close();
    }

    protected void hidePreloader(){
        notifyPreloader(new Preloader.StateChangeNotification(Preloader.StateChangeNotification.Type.BEFORE_START));
    }

    protected static void launchApp(Class<? extends AbstractJavaFxApplicationSupport> appClass, String[] args) {
        AbstractJavaFxApplicationSupport.savedArgs = args;
        LauncherImpl.launchApplication(appClass, AppPreloader.class, args);
    }
}
