package tech.aistar.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 本类功能:
 *
 * @author cxylk
 * @date 2020/8/31 10:26
 */
@FunctionalInterface
public interface IPrepareCallback {
    PreparedStatement handler(Connection conn) throws SQLException;
}
