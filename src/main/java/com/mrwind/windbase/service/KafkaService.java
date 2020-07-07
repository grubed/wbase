package com.mrwind.windbase.service;

import com.mrwind.windbase.common.util.TextUtils;
import com.mrwind.windbase.dto.BatchAddContactDTO;
import com.mrwind.windbase.dto.CreateUserAddressDTO;
import com.mrwind.windbase.dto.RegisterAccountBO;
import com.mrwind.windbase.entity.mysql.User;
import com.mrwind.windbase.mq.MultiIOSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.support.MessageBuilder;

/**
 * Created by CL-J on 2019/3/25.
 */

@EnableBinding(MultiIOSource.class)
public class KafkaService {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;
    @Autowired
    private MultiIOSource multiIOSource;

    @StreamListener("input1")
    public void newUserAndFriend(BatchAddContactDTO dto) {

        logger.info("获取到新加用户消息");
        logger.info(dto.toString());
        //新建用户
        if (dto.getNewUser() == null) {return;}
        RegisterAccountBO bo = new RegisterAccountBO();
        bo.setName(dto.getNewUser().getName());
        bo.setAvatar(dto.getNewUser().getAvatar());
        bo.setTel(dto.getNewUser().getTel());
        bo.setCountryCode(86);
        User user = userService.register(bo);
        logger.info("new user是");
        logger.info(user.getUserId());

        logger.info(dto.getNewUser().toString());

        //保存地址
       if (dto.getNewUser().getAddress()!=null) {
           dto.getNewUser().getAddress().setUserId(user.getUserId());
           CreateUserAddressDTO createUserAddressDTO = new CreateUserAddressDTO();
           createUserAddressDTO.setUserId(user.getUserId());
           createUserAddressDTO.setShopId(dto.getNewUser().getAddress().getShopId());
           createUserAddressDTO.setUserAddrSort(dto.getNewUser().getAddress().getUserAddrSort());
           createUserAddressDTO.setUserAddress(dto.getNewUser().getAddress().getUserAddress());
           createUserAddressDTO.setUserAddressDetail(dto.getNewUser().getAddress().getUserAddressDetail());
           createUserAddressDTO.setUserLat(dto.getNewUser().getAddress().getUserLat());
           createUserAddressDTO.setUserLng(dto.getNewUser().getAddress().getUserLng());
           createUserAddressDTO.setUserCity(dto.getNewUser().getAddress().getUserCity());
           createUserAddressDTO.setUserCountry(dto.getNewUser().getAddress().getUserCountry());
           createUserAddressDTO.setUserLandMark(dto.getNewUser().getAddress().getUserLandMark());
           createUserAddressDTO.setUserDistrict(dto.getNewUser().getAddress().getUserDistrict());
           createUserAddressDTO.setUserProvince(dto.getNewUser().getAddress().getUserProvince());
           createUserAddressDTO.setIsDefault(dto.getNewUser().getAddress().getIsDefault());
           userService.createUserAddress(createUserAddressDTO);
       }
        //返回给userId
        dto.getNewUser().setId(user.getUserId());

        multiIOSource.output2().send(MessageBuilder.withPayload(dto).build());

        logger.info("已发送回复");
        logger.info(dto.toString());
    }
}
