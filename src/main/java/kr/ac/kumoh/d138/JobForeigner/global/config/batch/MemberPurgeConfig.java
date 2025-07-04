package kr.ac.kumoh.d138.JobForeigner.global.config.batch;

import kr.ac.kumoh.d138.JobForeigner.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class MemberPurgeConfig extends DefaultBatchConfiguration {
    private final MemberRepository memberRepository;

    private static final long DAYS = 7;
    private static final int CHUNK_SIZE = 100;

    @Bean
    public Job memberPurgeJob() {
        return new JobBuilder("memberPurgeJob", jobRepository())
                .start(memberPurgeStep())
                .build();
    }

    @Bean
    @JobScope
    public Step memberPurgeStep() {
        return new StepBuilder("purgeStep", jobRepository())
                .tasklet((contribution, chunkCtx) -> {
                    log.info("회원 탈퇴 후 7일 이상 경과된 모든 회원을 물리적으로 삭제합니다.");
                    LocalDateTime threshold = LocalDateTime.now().minusDays(DAYS);
                    int deleted;
                    do {
                        deleted = memberRepository.hardDeleteByDeletedAtBefore(threshold, CHUNK_SIZE);
                        if (deleted > 0) {
                            log.info("회원 탈퇴 후 7일 이상 경과된 회원 {}명을 물리적으로 삭제했습니다.", deleted);
                        }
                    } while (deleted > 0);
                    log.info("회원 탈퇴 후 7일 이상 경과된 모든 회원을 물리적으로 삭제했습니다.");
                    return RepeatStatus.FINISHED;
                }, getTransactionManager())
                .build();
    }
}
