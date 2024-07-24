package com.today.weather.alarmy.dao;


import com.today.weather.alarmy.model.CodeModel;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface AlarmyDao {

    List<CodeModel> readCodeList();

    CodeModel readCode(String code);
}
