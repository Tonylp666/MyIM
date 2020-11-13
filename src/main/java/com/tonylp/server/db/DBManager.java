package com.tonylp.server.db;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DBManager {
    private static final Log LOG = LogFactory.getLog(DBManager.class);

    private static String dbType = "mysql";

    public static void executeSql(String sql){
        DBHelper helper = null;
        if (dbType.equals("mysql")){
            helper = new MySQLHelper();
        }
        LOG.info(" Sql is : " + sql);
        if (helper != null) {
            helper.executeSql(sql);
            helper.execute();
        }
    }

    public static DBHelper executeForStatement(String sql){
        DBHelper dbHelper = null;
        if(dbType.equals("mysql")){
            dbHelper = new MySQLHelper();
        }
        LOG.info(" Sql is : " + sql);
        if (dbHelper != null) {
            dbHelper.executeSql(sql);
        }
        return dbHelper ;
    }

}
