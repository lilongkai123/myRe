package com.fh.loginregister.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.loginregister.model.Login;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface LoginMapper extends BaseMapper<Login> {
}
