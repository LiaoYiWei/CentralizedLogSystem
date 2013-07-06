package com.hp.et.log.rules;

import groovy.lang.GroovyClassLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

import com.hp.et.log.configure.Configure;

public class AppRuleEngineLoader implements IAppRuleEngineLoader{

	private String groovyFileLocation = Configure.getProperty("groovy.file.location");
	private String groovyFileName = Configure.getProperty("groovy.file.name");
	
	public IRuleEngine loadRuleEngine(String appId) {

		ClassLoader ruleService = this.getClass().getClassLoader();
		GroovyClassLoader loader = new GroovyClassLoader(ruleService);

		// Get the resource link/name
		String fileURL = getGroovyFileURL(groovyFileLocation,groovyFileName, appId);
		// get the groovy file
		IRuleEngine ruleEngine = null;
		try {
			File file = new File(fileURL);
			Class<?> groovyClass = loader.parseClass(file);
			ruleEngine = (IRuleEngine) groovyClass.newInstance();
			return ruleEngine;
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Groovy file does not exist");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Could not load Rule Engine");
		}

	}
	
	private String getGroovyFileURL(String dir, String fileName, String appId) {
		StringBuilder fileURL = new StringBuilder();
		fileURL.append(dir);
		fileURL.append(fileName);
		fileURL.append(appId);
		fileURL.append(".groovy");
		return fileURL.toString();
	}


}
