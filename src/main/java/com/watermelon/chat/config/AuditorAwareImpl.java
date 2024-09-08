package com.watermelon.chat.config;

import java.util.Optional;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        // TODO: 추후에 Spring Security 적용하여 사용자 정보를 반환하도록 수정해야함
        return Optional.of("admin");
    }
}
