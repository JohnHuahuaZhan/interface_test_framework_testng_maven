package interface_test_framework_testng_maven.util.db;

import interface_test_framework_testng_maven.datasource.DataSourceFactory;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by xifan on 2019/1/11.
 */
public class DbTools {
    public static <T> List<T> getData(String sql, String dataBase, Class<? extends T> classEnter, Object... params) throws SQLException {
        QueryRunner qr = new QueryRunner(DataSourceFactory.getInstance().getDataSource(dataBase));
        return qr.query(sql, new BeanListHandler<T>(classEnter), params);
    }

    public static int updateData(String sql, String dataBase, Object... params) throws SQLException {
        QueryRunner qr = new QueryRunner(DataSourceFactory.getInstance().getDataSource(dataBase));
        return qr.update(sql, params);
    }
}
