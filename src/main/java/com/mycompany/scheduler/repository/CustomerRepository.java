
package com.mycompany.scheduler.repository;

import com.mycompany.scheduler.model.Customer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jdharri
 */
public class CustomerRepository {

    private static final Logger LOG = Logger.getLogger(CustomerRepository.class.getName());
    private final Connection con;

    public CustomerRepository(String driverClassName, String dbUrl, String user, String password) throws SQLException, ClassNotFoundException {
        Class.forName(driverClassName);
        con = DriverManager.getConnection(dbUrl, user, password);
    }

    /**
     * Generic query method
     *
     * @param query
     * @return
     */
    public ResultSet query(String query) {
        try (
                Statement statement = con.createStatement();
                ResultSet result = statement.executeQuery(query);) {
            return result;
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, "The query didnt work{0}", ex);
        }
        return null;
    }

    /**
     * Get all customers
     *
     * @return {@link List}{@link Customer}
     * @throws SQLException
     */
    public List<Customer> getAllCustomers() throws SQLException {
        ResultSet results = query("select * from customer");
        List<Customer> customers = new ArrayList<>();
        while (results.next()) {

            customers.add(createCustomerFromResult(results));
        }
        return customers;

    }

    /**
     * find one customer by Id
     *
     * @param id
     * @return {@link Customer}
     * @throws SQLException
     */
    public Customer getCustomerById(final String id) throws SQLException {
        ResultSet result = query("select * from customer where customerId = " + id);
        return createCustomerFromResult(result);
    }

    /**
     * Creates a {@link Customer} from a {@link ResultSet}
     *
     * @param results
     * @return {@link Customer}
     * @throws SQLException
     */
    private Customer createCustomerFromResult(ResultSet results) throws SQLException {
        Customer c = new Customer();
        c.setActive(results.getBoolean("active"));
        c.setAddressId(results.getInt("addressId"));
        c.setCreateDate(results.getDate("createDate"));
        c.setCreatedBy(results.getString("createdBy"));
        c.setCustomerId(results.getInt("customerId"));
        c.setCustomerName(results.getString("customerName"));
        c.setLastUpdate(results.getDate("lastUpdate"));
        c.setLastUpdateBy(results.getString("lastUpdateBy"));
        return c;
    }
}
