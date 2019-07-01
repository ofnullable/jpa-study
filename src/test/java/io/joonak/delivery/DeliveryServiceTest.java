package io.joonak.delivery;

import io.joonak.account.domain.Address;
import io.joonak.delivery.domain.Delivery;
import io.joonak.delivery.dto.DeliveryDto;
import io.joonak.delivery.exception.DeliveryNotFoundException;
import io.joonak.delivery.repository.DeliveryRepository;
import io.joonak.delivery.service.DeliveryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static io.joonak.utils.TestUtils.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class DeliveryServiceTest {

    @InjectMocks
    private DeliveryService deliveryService;

    @Mock
    private DeliveryRepository deliveryRepository;

    private Address address = buildAddress();
    private DeliveryDto.CreationRequest dto = buildDeliveryCreationRequest(address);

    @Test
    public void 배송정보_생성() {
        // given
        given(deliveryRepository.save(any(Delivery.class)))
                .willReturn(dto.toEntity());

        // when
        var delivery = deliveryService.create(dto);

        // then
        assertThat(delivery.getAddress(), is(address));
    }

    @Test
    public void 배송상태_업데이트() {
        // given
        var updateDto = buildDeliveryUpdateRequest();

        given(deliveryRepository.findById(any(Long.class)))
                .willReturn(Optional.of(dto.toEntity()));

        // when
        var result = deliveryService.updateDelivery(any(Long.class), updateDto.getStatus());

        // then
        assertThat(result.getAddress(), is(address));
        assertThat(result.getLogs().get(0).getStatus(), is(updateDto.getStatus()));
    }

    @Test
    public void 존재하는_배송정보() {
        // given
        given(deliveryRepository.findById(any(Long.class))).willReturn(Optional.of(dto.toEntity()));

        // when
        var delivery = deliveryService.findById(any(Long.class));

        // then
        assertThat(delivery.getAddress(), is(address));
    }

    @Test(expected = DeliveryNotFoundException.class)
    public void 존재하지_않는_배송정보() {
        // given
        given(deliveryRepository.findById(any(Long.class))).willThrow(DeliveryNotFoundException.class);

        // when
        deliveryService.findById(any(Long.class));

        // then
        // throw exception
    }

}