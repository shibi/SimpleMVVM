package com.bzcode.simplemvvm.domain.utils;

import com.bzcode.tutorial.data.local.entity.UserEntity;
import com.bzcode.tutorial.data.remote.dto.login.User;

/**
 * @author Shibin
 * created on 01-08-2023 at 11:34
 */
public class ModelConverter {


    public static UserEntity UserToUserEntity(User user){
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(user.getUserid());
        userEntity.setName(user.getName());
        userEntity.setEmail(user.getEmail());
        userEntity.setPhone(user.getPhone());
        return userEntity;
    }

    public static User UserEntitiyToUser(UserEntity userEntity){
        User user = new User();
        user.setUserid(userEntity.getUserId());
        user.setName(userEntity.getName());
        user.setEmail(userEntity.getEmail());
        user.setPhone(userEntity.getPhone());
        return user;
    }
}
