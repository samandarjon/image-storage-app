package io.storage.imagestorageapp.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import io.storage.imagestorageapp.config.props.S3ClientProps;
import io.storage.imagestorageapp.config.props.SNSClientProps;
import io.storage.imagestorageapp.config.props.SQSClientProps;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;

/**
 * Author: Samandar_Akbarov
 * Date: 10/28/2022
 */
@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class ApplicationConfig {

    private final S3ClientProps s3ClientProps;
    private final SNSClientProps snsClientProps;
    private final SQSClientProps sqsClientProps;
    private BasicAWSCredentials basicAWSCredentials;

    @PostConstruct
    public void init() {
        this.basicAWSCredentials = new BasicAWSCredentials(s3ClientProps.getKey(), s3ClientProps.getSecret());

    }

    @Bean
    public AmazonS3 s3Client() {
        // Get Amazon S3 client and return the S3 client object
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .withRegion(s3ClientProps.getRegion())
                .build();
    }

    @Bean
    public TransferManager transferManager(AmazonS3 s3Client) {
        return TransferManagerBuilder.standard()
                .withS3Client(s3Client)
                .build();
    }

    @Bean
    public AmazonSNS snsClient() {
        return AmazonSNSClient
                .builder()
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .withRegion(snsClientProps.getRegion())
                .build();
    }

    @Bean
    public AmazonSQS sqsClient() {
        return AmazonSQSClient
                .builder()
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .withRegion(sqsClientProps.getRegion())
                .build();
    }
}
