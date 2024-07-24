package com.today.weather.alarmy.dao;


import com.today.weather.alarmy.model.CodeModel;

import java.util.List;


public interface AlarmyDao {

    List<CodeModel> readCodeList();
}
