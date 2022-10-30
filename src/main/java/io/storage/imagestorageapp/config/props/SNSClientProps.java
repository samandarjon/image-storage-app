package io.storage.imagestorageapp.config.props;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.stereotype.Component;

/**
 * Author: Samandar_Akbarov
 * Date: 10/28/2022
 */
@Data
@ConfigurationProperties(prefix = "aws.sns")
@Component
public class SNSClientProps {
    private String region;
    private String topicArn;
}
