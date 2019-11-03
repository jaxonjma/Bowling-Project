package com.jaxon.bowling.reducer.impl;

import com.jaxon.bowling.reducer.MapReducer;

public class CounterReducer<T> implements MapReducer<T, Integer> {
	
	@Override
	public Integer map(T type) {
		return 1;
	}
	
	@Override
	public Integer reduce(Integer actual, Integer next) {
		return actual + next;
	}
	
	@Override
	public Integer initialValue() {
		return 0;
	}
	
}
