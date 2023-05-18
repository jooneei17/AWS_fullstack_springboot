package com.example.demo.service;

import com.example.demo.mapper.HomeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class HomeService {
    @Autowired
    private HomeMapper homeMapper;

    public String getTime(){
        return homeMapper.getTime();
    }

    public List<Map<String, Object>> getList() {
        return homeMapper.getList();
    }

}
