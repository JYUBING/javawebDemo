package com.jiahua.service;

import com.jiahua.pojo.TDocType;

import java.sql.SQLException;
import java.util.List;

public interface TDocTypeService {

    public List<TDocType> getAllTypeList() throws SQLException;
}
