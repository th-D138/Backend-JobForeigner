package kr.ac.kumoh.d138.JobForeigner.global.config.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
@ConfigurationProperties(prefix = "job-foreigner.cors")
public class CorsProperties {
    private List<String> origins = Collections.emptyList();
}
