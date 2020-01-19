package kiseok.demoinflearnrestapi.configs;

import kiseok.demoinflearnrestapi.accounts.Account;
import kiseok.demoinflearnrestapi.accounts.AccountRepository;
import kiseok.demoinflearnrestapi.accounts.AccountRole;
import kiseok.demoinflearnrestapi.accounts.AccountService;
import kiseok.demoinflearnrestapi.common.AppProperties;
import kiseok.demoinflearnrestapi.common.BaseControllerTest;
import kiseok.demoinflearnrestapi.common.TestDescription;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthServerConfigTest extends BaseControllerTest {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AppProperties appProperties;

    @Before
    public void setUp() {
        accountRepository.deleteAll();
    }

    @Test
    @TestDescription("인증 토큰을 발급받는 테스트")
    public void getAuthToken() throws Exception {
        Account account = Account.builder()
                .email(appProperties.getUsername())
                .password(appProperties.getPassword())
                .roles(Collections.singleton(AccountRole.USER))
                .build();
        this.accountService.saveAccount(account);

        // Given
        this.mockMvc.perform(post("/oauth/token")
                .with(httpBasic(appProperties.getClientId(), appProperties.getClientSecret()))    // basicAuth 라는 헤더를 만듦
                .param("username", appProperties.getUsername())
                .param("password", appProperties.getPassword())
                .param("grant_type", "password"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token").exists());

    }
}
