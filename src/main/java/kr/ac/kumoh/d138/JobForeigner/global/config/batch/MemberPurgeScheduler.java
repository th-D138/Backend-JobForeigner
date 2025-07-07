package kr.ac.kumoh.d138.JobForeigner.global.config.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberPurgeScheduler {
    private final JobLauncher jobLauncher;
    private final MemberPurgeConfig batchConfig;

    @Async
    @Scheduled(cron = "0 30 3 * * *")
    public void run() {
        Map<String, JobParameter<?>> parameters = new HashMap<>();
        parameters.put("time", new JobParameter<>(System.currentTimeMillis(), Long.class));
        JobParameters jobParameters = new JobParameters(parameters);
        try {
            jobLauncher.run(batchConfig.memberPurgeJob(), jobParameters);
        } catch (JobExecutionAlreadyRunningException |
                 JobInstanceAlreadyCompleteException |
                 JobParametersInvalidException |
                 JobRestartException e
        ) {
            log.error(e.getMessage());
        }
    }
}
