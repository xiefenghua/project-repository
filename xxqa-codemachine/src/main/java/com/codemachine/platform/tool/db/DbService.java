package com.codemachine.platform.tool.db;

import com.codemachine.platform.model.FieldBean;

import java.util.List;

public interface DbService {

    /**
     * 根据表名获取所有的列信息
     *
     * @param tableName
     * @return
     */
    public List<FieldBean> getAllColums(String tableName);

}
