package kr.ac.kumoh.d138.JobForeigner.global.jwt.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "job-foreigner.jwt.cryptographic-key")
public class KeyProperties {
    private String salt;
}
