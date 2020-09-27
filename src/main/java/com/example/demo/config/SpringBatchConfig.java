package com.example.demo.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.example.demo.mapper.CustomerDBRowMapper;
import com.example.demo.model.Customer;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private DataSource dataSource;
	

	private Resource outputResource = new FileSystemResource("output/users_output.csv");
	
	@Bean
    public JdbcCursorItemReader<Customer> reader1(){
    	JdbcCursorItemReader<Customer> cursorItemReader = new JdbcCursorItemReader<>();
    	cursorItemReader.setDataSource(dataSource);
    	cursorItemReader.setSql("select CustomerID, FirstName, LastName from [dbo].[Customer2] order by CustomerID");
    	cursorItemReader.setRowMapper(new CustomerDBRowMapper());
    	System.out.println(cursorItemReader);
    	System.out.println("step 1 reader !!!!!!!!!!!!");
    	return cursorItemReader; 
    }
	

	
	@Bean
	public FlatFileItemWriter<Customer> writer1(){
		FlatFileItemWriter<Customer> writer = new FlatFileItemWriter<Customer>();
		writer.setResource(outputResource);
	
		DelimitedLineAggregator<Customer> lineAggregator = new DelimitedLineAggregator<Customer>();
		lineAggregator.setDelimiter(",");
		
		BeanWrapperFieldExtractor<Customer> fieldExtractor = new BeanWrapperFieldExtractor<Customer>();
		fieldExtractor.setNames(new String[] {"CustomerID","FirstName","LastName"});
		lineAggregator.setFieldExtractor(fieldExtractor);
		
		writer.setLineAggregator(lineAggregator);
		System.out.println("inside Writer 1!!");
		writer.setShouldDeleteIfExists(true);
		return writer;
	}
    	
	
	@Bean
    public Step step1() throws Exception{	// Step 1 - Read DB and Write to CSV
    	return stepBuilderFactory.get("step1")
    			.<Customer,Customer>chunk(100)
    			.reader(reader1())
//    			.processor(processor2())
    			.writer(writer1())
    			.build();
    }
	
	@Bean
    public Job job() throws Exception{
    	return this.jobBuilderFactory.get("BATCH JOB")
    			.incrementer(new RunIdIncrementer())
    			.start(step1())
    			.build();
    }
}
