package com.nzhao.dao.user;

import com.nzhao.dao.BaseDao;
import com.nzhao.pojo.User;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 11507
 */
public class UserDaoImpl implements UserDao {
    @Override
    public User getLoginUser(Connection connection, String userCode) {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        // 如果connection不为空，则继续执行
        if (connection != null) {
            // 用问号保证安全，让sql预编译
            String sql = "SELECT * FROM `smbms_user` WHERE userCode = ? ";
            // 执行sql需要的参数
            Object[] params = {userCode};

            try {
                resultSet = BaseDao.execute(connection, sql, params, resultSet, preparedStatement);
                // 获取的结果集遍历使用
                if (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setUserCode(resultSet.getString("userCode"));
                    user.setUserName(resultSet.getString("userName"));
                    user.setUserPassword(resultSet.getString("userPassword"));
                    user.setGender(resultSet.getInt("gender"));
                    user.setBirthday(resultSet.getDate("birthday"));
                    user.setPhone(resultSet.getString("phone"));
                    user.setAddress(resultSet.getString("address"));
                    user.setUserRole(resultSet.getInt("userRole"));
                    user.setCreateBy(resultSet.getInt("createBy"));
                    user.setCreationDate(resultSet.getDate("creationDate"));
                    user.setModifyBy(resultSet.getInt("modifyBy"));
                    user.setModifyDate(resultSet.getDate("Date"));
                }
                BaseDao.release(null, preparedStatement, resultSet);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public List<User> findUser() {
        return null;
    }

    @Override
    public User getLoginUser(Connection connection, String userCode, String password) {
        ResultSet resultSet = null;
        String sql = "SELECT * FROM smbms_user WHERE usercode = ? AND userpassword = ? ";
        Object[] params = {userCode, password};
        PreparedStatement preparedStatement = null;
        User user = null;
        try {
            resultSet = BaseDao.execute(connection, sql, params, resultSet, preparedStatement);
            // 处理resultSet
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUserCode(resultSet.getString("userCode"));
                user.setUserName(resultSet.getString("userName"));
                user.setUserPassword(resultSet.getString("userPassword"));
            }
            return user;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.release(connection, preparedStatement, resultSet);
        }
        return null;
    }

    @Override
    public int updatePwd(Connection connection, Integer id, String password) {
        String sql = "UPDATE smbms_user SET userpassword = ? WHERE id = ? ";
        Object[] params = {password, id};
        PreparedStatement preparedStatement = null;
        int execute = 0;
        try {
            execute = BaseDao.execute(connection, sql, params, preparedStatement);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.release(connection, preparedStatement, null);
        }
        return execute;
    }

    @Override
    public int getUserCount(Connection connection, String userName, String roleName) {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        int count = 0;
        // 使用ArrayList存放参数
        ArrayList<Object> list = new ArrayList<>();
        Object[] parems = null;
        // 使用COUNT(1)的查询效率比COUNT(*)快很多倍
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(1) FROM smbms_user u LEFT JOIN smbms_role r ON u.userRole = r.id WHERE 1=1");
        if (StringUtils.isNotBlank(userName)) {
            sql.append(" AND u.userName LIKE  ? ");
            list.add("%" + userName + "%");
        }
        if (StringUtils.isNotBlank(roleName)) {
            sql.append(" AND r.roleName= ? ");
            list.add(roleName);
        }
        try {
            if (list.size() > 0) {
                parems = list.toArray();
            }
            resultSet = BaseDao.execute(connection, sql.toString(), parems, resultSet, preparedStatement);
            if (resultSet != null && resultSet.next()) {
                count = resultSet.getInt("COUNT(1)");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.release(connection, preparedStatement, resultSet);
        }
        return count;
    }
}
