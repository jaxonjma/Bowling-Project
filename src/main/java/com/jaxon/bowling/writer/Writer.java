package com.jaxon.bowling.writer;

import java.util.stream.Stream;

public interface Writer<T> {
	
	Integer write(Stream<T> list);
	
	Integer merge(Stream<T> list);
	
}