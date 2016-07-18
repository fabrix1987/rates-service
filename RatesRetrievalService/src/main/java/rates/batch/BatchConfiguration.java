package rates.batch;

import javax.sql.DataSource;

import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;

import rates.entity.Rate;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	public DataSource dataSource;
	
	@Value("classpath:*.DAT")
	private Resource[] resources;

	
	@Bean 
	public MyMultiResourceItemReader myMultiReader() {

		return new MyMultiResourceItemReader(resources);
		
	}

	
	@Bean
	public RateItemProcessor processor() {
		return new RateItemProcessor();
	}

	@Bean
	public JdbcBatchItemWriter<Rate> writer() {
		JdbcBatchItemWriter<Rate> writer = new JdbcBatchItemWriter<Rate>();
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Rate>());
		writer.setSql(
				"INSERT INTO rate (buyCurrency, file, rate, sellCurrency, validDate) VALUES (:buyCurrency, :file, :rate, :sellCurrency, :validDate)");
		writer.setDataSource(dataSource);
		return writer;
	}


	@Bean
	public JobExecutionListener listener() {
		return new JobCompletionNotificationListener(new JdbcTemplate(dataSource));
	}
	
	
	@Bean
	public ItemReadListener<RateLine> readListener() {
		return new CustomItemReaderListener();
	}
	
	@Bean
	public ItemProcessListener<RateLine, Rate> processListener() {
		return new CustomItemProcessListener();
	}

	@Bean
	public Job importRatesJob() {
		return jobBuilderFactory.get("importRatesJob").incrementer(new RunIdIncrementer())
				.flow(step1()).end().build();
	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1").<RateLine, Rate> chunk(10).reader(myMultiReader()).listener(readListener()).processor(processor()).listener(processListener())
				.writer(writer()).build();
	}


	}
