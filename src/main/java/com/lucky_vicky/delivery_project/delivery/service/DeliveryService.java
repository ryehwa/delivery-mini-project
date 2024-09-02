package com.lucky_vicky.delivery_project.delivery.service;

import com.lucky_vicky.delivery_project.delivery.domain.Delivery;
import com.lucky_vicky.delivery_project.delivery.dto.DeliveryRequestDto;
import com.lucky_vicky.delivery_project.delivery.dto.DeliveryResponseDto;
import com.lucky_vicky.delivery_project.delivery.repository.DeliveryRepository;
import com.lucky_vicky.delivery_project.global.exception.BusinessLogicException;
import com.lucky_vicky.delivery_project.global.exception.ExceptionCode;
import com.lucky_vicky.delivery_project.user.domain.User;
import com.lucky_vicky.delivery_project.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.lucky_vicky.delivery_project.global.exception.ExceptionCode.*;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final UserRepository userRepository;
    @Transactional
    public DeliveryResponseDto createAddress(String username, DeliveryRequestDto deliveryRequestDto) {
        //entity로 변환
        Delivery delivery = new Delivery(deliveryRequestDto);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        //To do : 이미 존재하는지 확인
        List<Delivery> allByUser = deliveryRepository.findAllByUser(user);
        if (allByUser.isEmpty()) {
            delivery.saveDefaultAddress();
        } else {
            boolean existsAddress = allByUser.stream()
                    .anyMatch(delivery1 -> delivery1.getAddress().equals(deliveryRequestDto.getAddress()));
            if (existsAddress) {
                throw new BusinessLogicException(DELIVERY_EXISTS);
            }
        }
        Delivery save = deliveryRepository.save(delivery);
        return new DeliveryResponseDto(save);
    }

    public Page<DeliveryResponseDto> getDeliveryList(String username, int page, int size, String sort) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessLogicException(USER_NOT_FOUND));
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sort));
        return deliveryRepository.findAllByUser(user, pageable)
                .map(DeliveryResponseDto::new);
        }

    @Transactional
    public DeliveryResponseDto updateDelivery(String username, UUID deliveryId, DeliveryRequestDto deliveryRequestDto) {
        Delivery delivery = getDelivery(username, deliveryId);
        delivery.update(deliveryRequestDto);
        return new DeliveryResponseDto(delivery);
    }

    @Transactional
    public void deleteDelivery(String username, UUID deliveryId) {
        Delivery delivery = getDelivery(username, deliveryId);
        deliveryRepository.delete(delivery);
    }

    private Delivery getDelivery(String username, UUID deliveryId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessLogicException(USER_NOT_FOUND));
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new BusinessLogicException(DELIVERY_NOT_FOUND));
        delivery.checkUser(user);
        return delivery;
    }

    //배송지 존재 여부 확인
    public Delivery isExistsDelivery(UUID deliveryId) {
        return deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new BusinessLogicException(DELIVERY_NOT_FOUND));
    }


    // 배송지 중복 여부 확인 메서드
//    public void isExistsDeliveryInList(Long id, String address) {
//        //회원id로 배송지 목록 찾기 List<>
//        List<Delivery> deliveryList = deliveryRepository.findAllByUserId(id);
//        //해당 List에서 address 중복되는 게 있는지 확인
//        //있으면 에러 처리
//        for(Delivery i : deliveryList) {
//            //띄어쓰기 제거
//            String repositoryDelivery = i.getAddress().replace(" ","");
//            String newDelivery = address.replace(" ","");
//            //exception
//            if(repositoryDelivery.equals(newDelivery)){
//                throw new IllegalArgumentException("이미 존재하는 배송지입니다.");
//            }
//        }
//    }


}
