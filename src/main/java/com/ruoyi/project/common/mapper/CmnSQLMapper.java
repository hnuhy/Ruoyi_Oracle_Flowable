package com.ruoyi.project.common.mapper;

import com.ruoyi.project.monitor.domain.SysJobLog;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

public interface CmnSQLMapper {
    public List<LinkedHashMap<String,Object>> selectSQL(@Param("sql") String sql);
}
