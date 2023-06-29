package com.immersion.board.testconfig;

import com.immersion.board.config.SecurityConfig;
import com.immersion.board.domain.UserAccount;
import com.immersion.board.repository.UserAccountRepository;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@Import(SecurityConfig.class)
public class TestSecurityConfig {

    @MockBean private UserAccountRepository accountRepository;

    @BeforeTestMethod
    public void securitySetUp() {
        given(accountRepository.findById(anyString())).willReturn(Optional.of(
                        UserAccount.of(
                                "test",
                                "pw",
                                "jiwon-test@email.com",
                                "uno-test",
                                "test memo"
                        )
                )
        );
    }

}
