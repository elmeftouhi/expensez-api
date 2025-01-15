package com.elmeftouhi.expensez.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.Optional;


@EnableJpaAuditing(dateTimeProviderRef = "auditingDateTimeProvider")
public class JpaAuditingConfig {
    @Bean
    public DateTimeProvider auditingDateTimeProvider(Clock clock){
        return () -> Optional.of(ZonedDateTime.now(clock));
    }

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.of("System");
    }
}
