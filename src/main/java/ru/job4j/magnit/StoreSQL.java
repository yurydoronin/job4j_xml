package ru.job4j.magnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class StoreSQL.
 *
 * @author Yury Doronin (doronin.ltd@gmail.com)
 * @version 1.0
 * @since 10.02.2020
 */
public class StoreSQL implements AutoCloseable {

    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(StoreSQL.class);

    /**
     *
     */
    private Connection connect;

    /**
     * @param config, .
     * @throws SQLException, .
     */
    public StoreSQL(Config config) throws SQLException {
        connect = DriverManager.getConnection(config.get("url"));
        connect.setAutoCommit(false);
    }

    /**
     *
     * @param size, .
     * @throws SQLException, .
     */
    public void generate(int size) throws SQLException {
        try (Statement st = connect.createStatement()) {
            st.executeUpdate(
                    "create table if not exists entry (field int not null)");
            try (PreparedStatement pst = connect.prepareStatement("select * from entry")) {
                if (pst.execute()) {
                    st.executeUpdate("delete from entry");
                }
            }
            try (PreparedStatement pst = connect.prepareStatement("insert into entry (field) values (?)")) {
                int count = 0;
                for (int index = 1; index <= size; index++) {
                    pst.setInt(1, index);
                    pst.addBatch();
                    count++;
                    if (count % 1000 == 0 || count == size) {
                        pst.executeBatch();
                        connect.commit();
                    }
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            connect.rollback();
        }
    }

    /**
     *
     * @return .
     * @throws SQLException, .
     */
    public List<XMLUsage.Entry> load() throws SQLException {
        List<XMLUsage.Entry> result = new ArrayList<>();
        try (Statement st = this.connect.createStatement();
             ResultSet rs = st.executeQuery("select field from entry")) {
            while (rs.next()) {
                result.add(new XMLUsage.Entry(rs.getInt(1)));
            }
        }
        return result;
    }

    /**
     * @throws Exception, .
     */
    @Override
    public void close() throws Exception {
        if (connect != null) {
            connect.close();
        }
    }
}
