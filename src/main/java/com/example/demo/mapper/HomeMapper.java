package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface HomeMapper {
    @Select("select now()")
    String getTime();

    @Select("select * from tbl_sample")
    public List<Map<String, Object>> getList();
}
