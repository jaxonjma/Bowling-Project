package com.jaxon.bowling.processor;

import java.nio.charset.Charset;

import com.jaxon.bowling.model.BaseEntity;

public interface IProcessor<T extends BaseEntity<?>> {
	
	void process(Class<T> clazz, String filename);
	
	void process(Class<T> clazz, String filename, Charset charset);
	
}
