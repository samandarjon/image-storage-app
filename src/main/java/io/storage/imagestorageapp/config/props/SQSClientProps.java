package io.storage.imagestorageapp.config.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Author: Samandar_Akbarov
 * Date: 10/28/2022
 */
@Data
@ConfigurationProperties(prefix = "aws.sqs")
@Component
public class SQSClientProps {

    private String region;
    private String queueUrl;
}
