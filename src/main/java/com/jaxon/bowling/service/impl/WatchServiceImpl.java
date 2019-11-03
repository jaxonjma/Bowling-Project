package com.jaxon.bowling.service.impl;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jaxon.bowling.service.WatchService;
import com.jaxon.bowling.worker.impl.WorkerImpl;

@Service
public class WatchServiceImpl implements WatchService{
	@Autowired
	private WorkerImpl worker;
	
	private final java.nio.file.WatchService watcher;
    private final Map<WatchKey, Path> keys;
    private static final Logger LOGGER = LoggerFactory.getLogger(WatchServiceImpl.class);
 
    /**
     * Creates a WatchService and registers the given directory
     * @throws IOException 
     */
    public WatchServiceImpl(Path dir) throws IOException {
        this.watcher = FileSystems.getDefault().newWatchService();
        this.keys = new HashMap<>();
        
        if(dir!=null) {
        walkAndRegisterDirectories(dir);}
    }
 
    /**
     * Register the given directory with the WatchService; This function will be called by FileVisitor
     * You can use ENTRY_DELETE, ENTRY_MODIFY too
     */
    private void registerDirectory(Path dir) throws IOException 
    {
        WatchKey key = dir.register(watcher, ENTRY_CREATE);
        keys.put(key, dir);
    }
 
    /**
     * Register the given directory, and all its sub-directories, with the WatchService.
     */
    private void walkAndRegisterDirectories(final Path start) throws IOException {
        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                registerDirectory(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }
 
    /**
     * Process all events for keys queued to the watcher
     */
    @Override
    public void processEvents() {
       while(true){
            WatchKey key;
            try {
				key = watcher.take();
				
				Path dir = keys.get(key);
	            if (dir == null) {
	                LOGGER.error("WatchKey not recognized!!");
	                continue;
	            }
	 
	            for (WatchEvent<?> event : key.pollEvents()) {
	                @SuppressWarnings("rawtypes")
	                WatchEvent.Kind kind = event.kind();
	 
	                if (kind == ENTRY_CREATE) {
	                   WatchEvent<Path> ev = cast(event);
                       Path directory = (Path) key.watchable();
                       Path fullPath = directory.resolve(ev.context());
                       worker.work(fullPath);
	                }
	            }
	 
	            boolean valid = key.reset();
	            if (!valid) {
	                keys.remove(key);
	 
	                if (keys.isEmpty()) {
	                    break;
	                }
	            }
			} catch (InterruptedException e) {
				LOGGER.error("Execution thread interrupted trying to start watcher");
				LOGGER.error(e.getMessage());
				Thread.currentThread().interrupt();
			}
        }
    }
    
    @SuppressWarnings("unchecked")
    private static <T> WatchEvent<T> cast(WatchEvent<?> event) {
        return (WatchEvent<T>)event;
    }
}
