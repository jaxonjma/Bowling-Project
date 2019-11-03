package com.jaxon.bowling.processor.impl;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jaxon.bowling.enums.States;
import com.jaxon.bowling.model.BaseEntity;
import com.jaxon.bowling.model.Process;
import com.jaxon.bowling.processor.IProcessor;
import com.jaxon.bowling.repository.IProcessRepository;
import com.jaxon.bowling.writer.Writer;

@Component
public abstract class ProcessorImpl<T extends BaseEntity<?>> implements IProcessor<T> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessorImpl.class);
	
	@Autowired
	private Writer<T> writer;
	
	@Autowired
	private IProcessRepository processRepository;
	
	@Override
	public void process(Class<T> clazz, String filename) {
		process(clazz, filename, StandardCharsets.UTF_8);
	}
	
	protected abstract Stream<T> read(Class<T> clazz, String filename, Charset charset);
	
	@Override
	public void process(Class<T> clazz, String filename, Charset charset) {
		Instant start = Instant.now();
		Process p = new Process();
		try {
			p.setState(States.CARGANDO.name());
			p.setStartDate(Date.from(start));
			p = processRepository.save(p);
			final Long identifier = p.getId();
			p.setRecords(
				writer.write(
					read(clazz, filename, charset)
						.map(entity -> this.complete(entity, identifier))
				)
			);
			p.setState(States.CARGADO.name());
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			p.setState(States.ERROR.name());
			p.setError(ex.getMessage());
		} finally {
			Instant stop = Instant.now();
			p.setEndDate(Date.from(stop));
			p = processRepository.save(p);
			LOGGER.info(String.format("Records - Written [%d] - Elapsed Time [%d] ms",
					Optional.ofNullable(p.getRecords()).orElse(0),ChronoUnit.MILLIS.between(start, stop))
			);
		}
	}
	
	private T complete(T instance, Long identifier) {
		instance.setProcess(identifier);
		return instance;
	}
	
}
