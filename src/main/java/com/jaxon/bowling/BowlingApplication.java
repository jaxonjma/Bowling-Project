package com.jaxon.bowling;

import static java.lang.System.exit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jaxon.bowling.service.WatchService;

@SpringBootApplication
public class BowlingApplication implements CommandLineRunner{
	
	@Autowired
	private  WatchService ws;

	public static void main(String[] args) {
		SpringApplication.run(BowlingApplication.class, args);
	}
	
    @Override
    public void run(String... args) throws Exception {
        if (args.length > 0) {
            ws.processEvents();
        }
        exit(0);
    }

}
