package io.storage.imagestorageapp.config.props;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Author: Samandar_Akbarov
 * Date: 10/28/2022
 */
@Data
@ConfigurationProperties(prefix = "aws.s3")
@Component
public class S3ClientProps {

    private String key;

    private String secret;

    private String region;
}
