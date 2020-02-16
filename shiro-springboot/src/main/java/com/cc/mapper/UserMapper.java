package com.cc.mapper;


import com.cc.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Repository
@Mapper
public interface UserMapper {

    User queryUserByName(String name);

}
