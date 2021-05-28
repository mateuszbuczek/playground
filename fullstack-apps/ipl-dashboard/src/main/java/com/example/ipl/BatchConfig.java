package com.example.ipl;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.RecordFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {

    private static final String INPUT_DATA_FILENAME = "ipl-data.csv";
    private static final String[] FIELD_NAMES = new String[] {
            "id","city","date","player_of_match","venue","neutral_venue","team1","team2","toss_winner","toss_decision","winner","result","result_margin","eliminator","method","umpire1","umpire2"
    };

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public FlatFileItemReader<MatchInput> reader() {
        return new FlatFileItemReaderBuilder<MatchInput>()
                .name("MatchItemReader")
                .resource(new ClassPathResource(INPUT_DATA_FILENAME))
                .delimited()
                .names(FIELD_NAMES)
                .fieldSetMapper(new RecordFieldSetMapper<>(MatchInput.class))
                .build();
    }

    @Bean
    public ItemProcessor<MatchInput, Match> processor() {
        return new MatchDataProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Match> writer(DataSource dataSource) {
    return new JdbcBatchItemWriterBuilder<Match>()
        .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider())
        .dataSource(dataSource)
        .sql(
            "INSERT INTO match (id,"
                + "city,"
                + "date,"
                + "player_of_match,"
                + "venue,"
                + "team1,"
                + "team2,"
                + "toss_winner,"
                + "toss_decision,"
                + "winner,"
                + "result,"
                + "result_margin,"
                + "umpire1,"
                + "umpire2) VALUES ("
                +" :id,"
                + ":city,"
                + ":date,"
                + ":playerOfMatch,"
                + ":venue,"
                + ":team1,"
                + ":team2,"
                + ":tossWinner,"
                + ":tossDecision,"
                + ":winner,"
                + ":result,"
                + ":resultMargin,"
                + ":umpire1,"
                + ":umpire2)")
        .build();
    }

    @Bean
    public Job iplProcessingJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory
                .get("iplProcessingJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter<Match> writer) {
        return stepBuilderFactory
                .get("step1")
                .<MatchInput, Match>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }
}
