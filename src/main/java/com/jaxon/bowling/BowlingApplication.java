package com.jaxon.bowling;

import static java.lang.System.exit;

import java.util.Timer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jaxon.bowling.service.WatchService;
import com.jaxon.bowling.task.ShowProcessTask;

@SpringBootApplication
public class BowlingApplication implements CommandLineRunner{
	
	@Autowired
	private  WatchService ws;
	
	@Autowired
	private ShowProcessTask mTask;
	
	@Value("${process.completedAt.every}")
	private int waitFor;

	public static void main(String[] args) {
		SpringApplication.run(BowlingApplication.class, args);
	}
	
    @Override
    public void run(String... args) throws Exception {
        if (args.length > 0) {
        	Timer t = new Timer();
            t.scheduleAtFixedRate(mTask, 0, waitFor);
            ws.processEvents();
        }
        exit(0);
    }

}
