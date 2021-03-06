package io.joonak.account.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    @NotBlank
    @Column(name = "address", nullable = false)
    private String address;

    @NotBlank
    @Column(name = "detail_address", nullable = false)
    private String detailAddress;

    @NotBlank
    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    @Builder
    public Address(String address, String detailAddres, String zipCode) {
        this.address = address;
        this.detailAddress = detailAddres;
        this.zipCode = zipCode;
    }
}
