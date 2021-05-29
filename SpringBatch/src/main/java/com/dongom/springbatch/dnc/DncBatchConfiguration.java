package com.dongom.springbatch.dnc;

import javax.batch.api.chunk.AbstractItemWriter;
import javax.batch.api.chunk.ItemReader;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.PassThroughFieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class DncBatchConfiguration {
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;

	@Bean
	public Job dncJob() {
		return jobBuilderFactory.get("dncJob")
				.start(dncStep1(null))
				.build();
	}

	@Bean
	@JobScope
	public Step dncStep1(@Value("#{jobParameters[requestDate]}") String requestDate) {
		log.info(">>>>> This is Step1");
		log.info(">>>>> requestDate = {}", requestDate);
		StepBuilder stepBuilder = stepBuilderFactory.get("dncStep");
		SimpleStepBuilder simpleStepBuilder = stepBuilder.<String, String>chunk(10);
		simpleStepBuilder.reader(dncreader(null));
		simpleStepBuilder.writer(dncwriter());
		return simpleStepBuilder.build();
	}

	@Bean
	@StepScope
	public FlatFileItemReader dncreader(@Value("#{jobParameters[requestDate]}") String requestDate) {
		FlatFileItemReaderBuilder reader = new FlatFileItemReaderBuilder();
		// DNC_YYYYYMMDD.dat
		String fileName = "DNC_" + requestDate + ".dat";
		reader.name("dncItemReader");
		reader.resource(new ClassPathResource(fileName));
		reader.lineTokenizer(new DelimitedLineTokenizer());
		reader.fieldSetMapper(new DncFieldSetMapper());
		return reader.build();
	}
	
	@Bean
	public ItemWriter dncwriter() {
		return new DncPrintItemWriter();
	}
}