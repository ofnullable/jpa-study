package io.joonak.account.domain;

import io.joonak.account.dto.AccountDto;
import io.joonak.common.domain.DateInfo;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@Table(name= "account")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Email email;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Embedded
    private Password password;

    @Embedded
    private Address address;

    @Embedded
    private DateInfo dateInfo;

    @Builder
    public Account(Email email, String firstName, String lastName, Password password, Address address) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.address = address;
    }

    public void updateAddress(AccountDto.UpdateAddressRequest dto) {
        this.address = dto.getAddress();
    }

}