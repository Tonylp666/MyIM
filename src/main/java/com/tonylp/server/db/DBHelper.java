package com.tonylp.server.db;

import com.tonylp.server.utils.JDBCUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DBHelper {

    private static final Log LOG = LogFactory.getLog(DBHelper.class);

    public static Connection conn = null;
    public static PreparedStatement statement = null;

    static {
        try {
            conn = JDBCUtils.getDataSource().getConnection();
        } catch (SQLException throwables) {
            LOG.error("MySQLHelper exeception, Get jdbc connect error.");
            throwables.printStackTrace();
        }
    }

    public abstract ResultSet executeQuery() ;

    public abstract boolean execute();


    public abstract void executeSql(String sql) ;

    public abstract void close();
}
