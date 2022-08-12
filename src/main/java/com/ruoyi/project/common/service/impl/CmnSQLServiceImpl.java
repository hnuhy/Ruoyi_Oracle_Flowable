package com.ruoyi.project.common.service.impl;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.project.common.mapper.CmnSQLMapper;
import com.ruoyi.project.common.service.ICmnSQLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CmnSQLServiceImpl implements ICmnSQLService {

    @Autowired
    private CmnSQLMapper cmnSQLMapper;

    /**
     * @param sqlId
     * @return
     */
    @Override
    public Object selectSQLData(LinkedHashMap<String, Object> sqlParams) {

        String sqlId = "";
        String strResultType = "";
        String strSQL = "";
        List<LinkedHashMap<String, Object>> lstSQL = new ArrayList<LinkedHashMap<String, Object>>();

        //获取SQL编号
        {
            if(sqlParams.containsKey("SQLId")){
                sqlId = sqlParams.get("SQLId").toString();
            }
        }

        //获取SQL的命令
        if(StringUtils.isNotEmpty(sqlId)){
            String strCurSQL = "SELECT CMD_TEXT, RESULT_TYPE FROM CMN_SQL WHERE ID = '"+sqlId+"' AND DEL_FLAG = '0'";
            List<LinkedHashMap<String, Object>> lstCurSQL = cmnSQLMapper.selectSQL(strCurSQL);
            if(lstCurSQL.size() == 1){
                HashMap hmResult  = lstCurSQL.get(0);
                strSQL = StringUtils.cast(hmResult.get("CMD_TEXT"));
                strResultType = StringUtils.cast(hmResult.get("RESULT_TYPE"));
            }
        }

        //处理参数
       if(StringUtils.isNotEmpty(strSQL) && StringUtils.isNotNull(strSQL)){
           String strCurSQL = "SELECT NAME FROM CMN_SQL_PARAM WHERE SQL_ID = '"+sqlId+"' ";
            List<LinkedHashMap<String, Object>> lstCurSQL = cmnSQLMapper.selectSQL(strCurSQL);

           for (HashMap keys: lstCurSQL) {
               String key = StringUtils.cast(keys.get("NAME"));
               if(sqlParams.containsKey(key)){
                   String val = sqlParams.get(key).toString();
                   strSQL = strSQL.replaceAll(":"+key,val);
               }
               else{
                   strSQL = strSQL.replaceAll(":"+key,"*");
               }
           }

        }

       //获取结果
        if(StringUtils.isNotEmpty(strSQL) && StringUtils.isNotNull(strSQL)){
            lstSQL = cmnSQLMapper.selectSQL(strSQL);

            if(strResultType.equals("L")){
                return lstSQL;
            }
            else if(strResultType.equals("O")){
                if(lstSQL.size() == 1){
                    return lstSQL.get(0);
                }
            }
        }

        return lstSQL;
    }
}
