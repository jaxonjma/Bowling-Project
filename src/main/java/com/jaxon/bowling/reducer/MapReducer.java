package com.jaxon.bowling.reducer;

public interface MapReducer<T, R> {
	
	R map(T type);
	
	R reduce(R actual, R next);
	
	R initialValue();
	
}
