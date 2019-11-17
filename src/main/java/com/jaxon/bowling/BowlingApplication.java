package com.jaxon.bowling;

import static java.lang.System.exit;

import java.nio.file.Paths;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jaxon.bowling.worker.impl.WorkerImpl;

@SpringBootApplication(scanBasePackages = {"com.jaxon.bowling"})
public class BowlingApplication implements CommandLineRunner{
	
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(BowlingApplication.class);
			
	@Autowired
	private WorkerImpl worker;

	public static void main(String[] args) {
		BasicConfigurator.configure();
		Logger.getRootLogger().setLevel(Level.WARN);
		SpringApplication app = new SpringApplication(BowlingApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.setLogStartupInfo(Boolean.FALSE);
		app.run(args);
	}
	
    @Override
    public void run(String... args) throws Exception {
    	if(args==null || args.length==0) {
    		LOGGER.warn("A file argument was not found, you must use the structure: \"java -jar path/to/jar path/to/yourFile.txt\" to run the application");
    	}else {
    		worker.work(Paths.get(String.join(" ",args)));
    	}
    	exit(0);
    }

}
