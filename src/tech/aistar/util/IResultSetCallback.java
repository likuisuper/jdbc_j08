package tech.aistar.util;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 本类功能:
 *
 * @author cxylk
 * @date 2020/8/31 10:26
 */
@FunctionalInterface
public interface IResultSetCallback<T> {
    T executeQuery(ResultSet rs) throws SQLException;
}
