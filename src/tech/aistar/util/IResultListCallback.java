package tech.aistar.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 本类功能:
 *
 * @author cxylk
 * @date 2020/8/31 10:39
 */
@FunctionalInterface
public interface IResultListCallback<T> {
    List<T> executeQuery(ResultSet rs) throws SQLException;
}
