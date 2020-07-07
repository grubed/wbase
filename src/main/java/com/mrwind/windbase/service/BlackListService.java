package com.mrwind.windbase.service;

import com.mrwind.windbase.entity.mysql.BlackList;
import com.mrwind.windbase.entity.mysql.UserFriend;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by CL-J on 2019/1/7.
 */
@Service
public class BlackListService extends BaseService{

    public BlackList moveToBlcakList(String userId, String target) {
        BlackList blackList = new BlackList(userId,target);
        BlackList has = blackListRepository.findByUserIdAndTarget(userId,target);
        if (has != null) {
            return has;
        }
        blackListRepository.save(blackList);
        UserFriend userFriend = userFriendRepository.get2UserRelation(userId,target);
        userFriendRepository.delete(userFriend);
        return blackList;
    }

    public int removeFromBlackList(String userId,String target) {
        userFriendRepository.save(new UserFriend(userId,target,0));
        return blackListRepository.deleteAllByUserIdAndTarget(userId,target);
    }

    public List<BlackList> allBlackList(String userId) {
        return blackListRepository.findAllByUserId(userId);
    }
}
