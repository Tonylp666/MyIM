package com.tonylp.server.db;

import com.tonylp.server.utils.JDBCUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLHelper extends DBHelper {
    private static final Log LOG = LogFactory.getLog(MySQLHelper.class);

    @Override
    public ResultSet executeQuery() {
        try {
            return statement.executeQuery();
        } catch (SQLException throwables) {
            LOG.error("MySQLHelper#execute() exeception, Get jdbc connect error.");
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean execute() {
        try {
            return statement.execute();
        } catch (SQLException throwables) {
            LOG.error("MySQLHelper#execute() exeception, Get jdbc connect error.");
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public void executeSql(String sql){

        try {
            statement = conn.prepareStatement(sql);
        } catch (SQLException throwables) {
            LOG.error("MySQLHelper#executeSql() exeception, Get jdbc statement error.");
            throwables.printStackTrace();
        }
    }

    @Override
    public void close() {

    }
}
