package com.example.demo.mapper;

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    int addUser(@Param("user") User user);
    User getUser(@Param("user") User user);
    User getUserById(@Param("user") User user);
    List<User> getAll();
    int updateUser(@Param("user") User user);
    int deleteUser(@Param("userId") Integer userId);
}
