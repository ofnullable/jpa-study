package io.joonak.account;

import io.joonak.account.domain.Account;
import io.joonak.account.domain.Email;
import io.joonak.account.dto.AccountDto;
import io.joonak.account.exception.AccountNotFoundException;
import io.joonak.account.exception.EmailDuplicationException;
import io.joonak.account.repository.AccountRepository;
import io.joonak.account.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static io.joonak.utils.TestUtils.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    private AccountDto.SignUpRequest signUpDto = buildSignUpRequest(buildEmail());

    @Test
    public void 회원가입() {
        //given
        given(accountRepository.save(any(Account.class)))
                .willReturn(signUpDto.toEntity());

        //when
        var account = accountService.create(signUpDto);

        //then
        verify(accountRepository, atLeastOnce()).save(any(Account.class));

        assertEqualAccount(signUpDto, account);
    }

    @Test
    public void 중복된_이메일의_회원가입() {
        //given
        given(accountRepository.existsByEmail(any(Email.class)))
                .willReturn(true);

        //when
        assertThrows(EmailDuplicationException.class, () -> accountService.create(signUpDto));

        //then
    }

    @Test
    public void 존재하는_계정_아이디() {
        //given
        given(accountRepository.findById(any(Long.class)))
                .willReturn(Optional.of(signUpDto.toEntity()));

        //when
        var account = accountService.findById(any(Long.class));

        //then
        verify(accountRepository, atLeastOnce()).findById(any(Long.class));

        assertEqualAccount(signUpDto, account);
    }

    @Test
    public void 존재하지_않는_계정_아이디() {
        //given
        given(accountRepository.findById(any(Long.class)))
                .willReturn(Optional.empty());

        //when
        assertThrows(AccountNotFoundException.class, () -> accountService.findById(any(Long.class)));

        //then
    }

    @Test
    public void 존재하는_이메일() {
        //given
        var entity = signUpDto.toEntity();
        given(accountRepository.findByEmail(any(Email.class)))
                .willReturn(Optional.of(entity));

        //when
        var account = accountService.findByEmail(entity.getEmail());

        //then
        verify(accountRepository, atLeastOnce()).findByEmail(entity.getEmail());

        assertEqualAccount(signUpDto, account);
    }

    @Test
    public void 존재하지_않는_이메일() {
        //given
        given(accountRepository.findByEmail(any(Email.class)))
                .willReturn(Optional.empty());

        //when
        assertThrows(AccountNotFoundException.class, () -> accountService.findByEmail(buildEmail()));
    }

    @Test
    public void 주소_수정() {
        //given
        var updateDto = buildAccountUpdateRequest();
        given(accountRepository.findById(any(Long.class)))
                .willReturn(Optional.ofNullable(signUpDto.toEntity()));

        //when
        var account = accountService.updateAddress(any(Long.class), updateDto);

        //then
        assertEqualAddress(updateDto, account);
    }

    @Test
    public void 이메일이_존재하는_경우() {
        //given
        given(accountRepository.existsByEmail(any()))
                .willReturn(true);

        //when
        var isExist = accountService.isExistedEmail(any());

        //then
        assertThat(isExist, is(true));
    }

}
