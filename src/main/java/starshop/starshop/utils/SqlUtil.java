package starshop.starshop.utils;

import cn.hutool.core.util.ObjectUtil;
import lombok.SneakyThrows;
import starshop.starshop.sqlite.Db;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lxh
 * @version 1.0
 * @description -
 * @date 2023/5/2
 */
public class SqlUtil {

    /**
     * 插入数据
     * @param sql    SQL语句
     * @param entity 实体类对象
     * @return 自增长主键ID
     */
    @SneakyThrows
    public static int insert(String sql, Object entity) {
        Connection conn = Db.getInstance().getConnection();
        int id = -1;
        PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        setParameters(pstmt, entity);
        pstmt.executeUpdate();
        ResultSet rs = pstmt.getGeneratedKeys();
        if (rs.next()) {
            id = rs.getInt(1);
        }
        closeResultSet(rs);
        closeStatement(pstmt);
        return id;
    }

    /**
     * 插入语句
     * @param sql    sql语句
     * @param params 参数
     * @return 主键ID
     */
    @SneakyThrows
    public static int insert(String sql, Object... params) {
        Connection conn = Db.getInstance().getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        setParameters(pstmt, params);
        int res = pstmt.executeUpdate();
        ResultSet rs = pstmt.getGeneratedKeys();
        closeResultSet(rs);
        closeStatement(pstmt);
        return res;
    }

    /**
     * 更新数据
     * @param sql    SQL语句
     * @param params 参数数组
     * @return 影响行数
     */
    @SneakyThrows
    public static int update(String sql, Object... params) {
        Connection conn = Db.getInstance().getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        setParameters(pstmt, params);
        int result = pstmt.executeUpdate();
        closeStatement(pstmt);
        return result;
    }

    /**
     * 查询多条记录，并将结果映射为指定的实体类对象列表（支持分页）
     * @param sql      SQL语句
     * @param clazz    实体类的Class对象
     * @param params   参数数组
     * @param pageNo   当前页码
     * @param pageSize 每页记录数
     * @param <T>      实体类的类型
     * @return 实体类对象列表
     */
    @SneakyThrows
    public static <T> List<T> queryForPage(String sql, Class<T> clazz, int pageNo, int pageSize, Object... params) {
        Connection conn = Db.getInstance().getConnection();
        // 构造分页查询的 SQL 语句
        StringBuilder sb = new StringBuilder();
        sb.append(sql);
        sb.append(" LIMIT ?");
        sb.append(" OFFSET ?");
        PreparedStatement pstmt = conn.prepareStatement(sb.toString());

        // 设置参数
        int paramIndex = params.length;
        setParameters(pstmt, params);
        pstmt.setInt(paramIndex + 1, pageSize);
        pstmt.setInt(paramIndex + 2, (pageNo - 1) * pageSize);

        ResultSet rs = pstmt.executeQuery();
        ResultSetMetaData metaData = rs.getMetaData();
        List<T> list = new ArrayList<>();
        while (rs.next()) {
            T obj = clazz.newInstance();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                String columnName = metaData.getColumnName(i);
                Object value = rs.getObject(i);
                String propertyName = underlineToCamel(columnName);
                setProperty(obj, propertyName, value);
            }
            list.add(obj);
        }
        closeResultSet(rs);
        closeStatement(pstmt);
        return list;
    }

    /**
     * 查询单个对象
     * @param sql    SQL语句
     * @param clazz  实体类
     * @param params 参数数组
     * @param <T>    实体类类型
     * @return 实体类对象
     */
    @SneakyThrows
    public static <T> T queryForObject(String sql, Class<T> clazz, Object... params) {
        Connection conn = Db.getInstance().getConnection();
        T result = null;
        PreparedStatement pstmt = conn.prepareStatement(sql);
        setParameters(pstmt, params);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            result = clazz.newInstance();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = underlineToCamel(metaData.getColumnName(i));
                Object columnValue = rs.getObject(metaData.getColumnName(i));
                setProperty(result, columnName, columnValue);
            }
        }
        closeResultSet(rs);
        closeStatement(pstmt);
        return result;
    }

    /**
     * 查询多个对象
     * @param sql    SQL语句
     * @param clazz  实体类
     * @param params 参数数组
     * @param <T>    实体类类型
     * @return 实体类对象列表
     */
    @SneakyThrows
    public static <T> List<T> queryForList(String sql, Class<T> clazz, Object... params) {
        Connection conn = Db.getInstance().getConnection();
        List<T> resultList = new ArrayList<>();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        setParameters(pstmt, params);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            T result = clazz.newInstance();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = underlineToCamel(metaData.getColumnName(i));
                Object columnValue = rs.getObject(metaData.getColumnName(i));
                setProperty(result, columnName, columnValue);
            }
            resultList.add(result);
        }
        closeResultSet(rs);
        closeStatement(pstmt);
        return resultList;
    }

    /**
     * 删除数据
     * @param sql    SQL语句
     * @param params 参数数组
     * @return 影响行数
     */
    @SneakyThrows
    public static int delete(String sql, Object... params) {
        Connection conn = Db.getInstance().getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        setParameters(pstmt, params);
        int result = pstmt.executeUpdate();
        closeStatement(pstmt);
        return result;
    }

    /**
     * 关闭结果集
     * @param rs 结果集
     */
    @SneakyThrows
    private static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            rs.close();
        }
    }

    /**
     * 关闭PreparedStatement
     * @param pstmt PreparedStatement
     */
    @SneakyThrows
    private static void closeStatement(PreparedStatement pstmt) {
        if (pstmt != null) {
            pstmt.close();
        }
    }

    /**
     * 设置PreparedStatement的参数
     * @param pstmt  PreparedStatement
     * @param params 参数数组
     */
    @SneakyThrows
    private static void setParameters(PreparedStatement pstmt, Object... params) {
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
        }
    }

    /**
     * 为实体类设置属性值
     * @param obj          实体类对象
     * @param propertyName 属性名
     * @param value        属性值
     */
    @SneakyThrows
    private static void setProperty(Object obj, String propertyName, Object value) {
        Field field = getFieldByName(obj.getClass(), propertyName);
        if (ObjectUtil.isNotEmpty(field)) {
            field.setAccessible(true);
            field.set(obj, value);
        }
    }

    /**
     * 获取实体类所有属性进行过滤
     * @param clazz     clazz
     * @param fieldName propertyName
     */
    public static Field getFieldByName(Class<?> clazz, String fieldName) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }
        return null;
    }

    /**
     * 将下划线命名的字符串转换为驼峰式命名的字符串
     * @param str 下划线命名的字符串
     * @return 驼峰式命名的字符串
     */
    private static String underlineToCamel(String str) {
        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (ch == '_') {
                upperCase = true;
            } else {
                if (upperCase) {
                    sb.append(Character.toUpperCase(ch));
                    upperCase = false;
                } else {
                    sb.append(ch);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 开始事务
     */
    public static void beginTransaction() throws SQLException {
        Connection conn = Db.getInstance().getConnection();
        conn.setAutoCommit(false);
    }

    /**
     * 提交事务
     */
    public static void commitTransaction() throws SQLException {
        Connection conn = Db.getInstance().getConnection();
        conn.commit();
        conn.setAutoCommit(true);
    }

    /**
     * 回滚事务
     */
    public static void rollbackTransaction() throws SQLException {
        Connection conn = Db.getInstance().getConnection();
        conn.rollback();
        conn.setAutoCommit(true);
    }
}
