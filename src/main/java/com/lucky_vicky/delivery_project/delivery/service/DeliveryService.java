package com.lucky_vicky.delivery_project.delivery.service;

import com.lucky_vicky.delivery_project.delivery.domain.Delivery;
import com.lucky_vicky.delivery_project.delivery.dto.DeliveryRequestDto;
import com.lucky_vicky.delivery_project.delivery.dto.DeliveryResponseDto;
import com.lucky_vicky.delivery_project.delivery.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    public DeliveryResponseDto createAddress(DeliveryRequestDto deliveryRequestDto) {
        //권한 체크

        //entity로 변환
        Delivery delivery = new Delivery(deliveryRequestDto);
        //이미 존재하는지 확인
        //isExistsDelivery(delivery.getAddress());
        //해당 회원이 가진 배송지가 아무것도 없다면 isDefault처리
        delivery.setDefault(true);
        //db 저장
        deliveryRepository.save(delivery);
        return new DeliveryResponseDto(delivery);
    }

    // 배송지 존재 여부 확인 메서드
//    public void isExistsDelivery(Long id, String address) {
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
