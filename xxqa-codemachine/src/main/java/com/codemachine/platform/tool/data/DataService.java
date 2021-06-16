package com.codemachine.platform.tool.data;

import com.codemachine.platform.model.TableConfig;

import java.util.Map;

public interface DataService {
    /**
     * 根据表名获取Vo模板要用的Map型数据
     *
     * @param tableName
     * @return
     */
    public Map<String, Object> getDbTemplateData(String tableName,
                                                 String classPre,
                                                 TableConfig tableConfig);

}
