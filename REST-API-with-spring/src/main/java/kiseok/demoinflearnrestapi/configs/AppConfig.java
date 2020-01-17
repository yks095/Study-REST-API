package kiseok.demoinflearnrestapi.configs;

import kiseok.demoinflearnrestapi.accounts.Account;
import kiseok.demoinflearnrestapi.accounts.AccountRole;
import kiseok.demoinflearnrestapi.accounts.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper()    {
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder()    {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public ApplicationRunner applicationRunner()    {
        return new ApplicationRunner() {

            @Autowired
            AccountService accountService;

            @Override
            public void run(ApplicationArguments args) throws Exception {
                Account account = Account.builder()
                        .email("kiseok@email.com")
                        .password("kiseok")
                        .roles(Collections.singleton(AccountRole.USER))
                        .build();

                accountService.saveAcoount(account);
            }
        };
    }

}
