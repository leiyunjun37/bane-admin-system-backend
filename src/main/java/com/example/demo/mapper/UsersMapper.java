package com.example.demo.mapper;

import com.example.demo.entity.Users;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * <p>
 * 'sys.user_summary_by_statement_type' is not BASE TABLE Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2023-10-10
 */
@Mapper
public interface UsersMapper extends BaseMapper<Users> {
    @Select("SELECT * FROM users WHERE is_delete = 0 LIMIT #{start}, 10")
    List<Users> selectUsers(Integer start);
}
