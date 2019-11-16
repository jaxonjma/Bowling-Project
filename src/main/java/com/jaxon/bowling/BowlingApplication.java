package com.jaxon.bowling;

import static java.lang.System.exit;

import java.nio.file.Paths;

import org.apache.log4j.BasicConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jaxon.bowling.worker.impl.WorkerImpl;

@SpringBootApplication(scanBasePackages = {"com.jaxon.bowling"})
public class BowlingApplication implements CommandLineRunner{
	
	@Autowired
	private WorkerImpl worker;

	public static void main(String[] args) {
		BasicConfigurator.configure();
		SpringApplication app = new SpringApplication(BowlingApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.setLogStartupInfo(Boolean.FALSE);
		app.run(args);
	}
	
    @Override
    public void run(String... args) throws Exception {
    	worker.work(Paths.get(args[0]));
        exit(0);
    }

}
