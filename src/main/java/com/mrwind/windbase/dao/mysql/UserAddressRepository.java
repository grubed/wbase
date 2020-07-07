package com.mrwind.windbase.dao.mysql;

import com.mrwind.windbase.entity.mysql.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by CL-J on 2018/12/10.
 */
public interface UserAddressRepository extends JpaRepository<UserAddress,Long>{

    List<UserAddress> findAllByUserId(String userId);

    //通过userId address userCountry  userProvince userCity userDistrict userLandMark确定某地址是否存在
    UserAddress findUserAddressByUserIdAndUserAddressAndUserCountryAndUserProvinceAndUserCityAndUserDistrictAndUserLandMark(String userId, String userAddress,
                                                                                                                 String userCountry,String userProvince,
                                                                                                                 String userCity,String userDistrict,String userLandMark);

}
