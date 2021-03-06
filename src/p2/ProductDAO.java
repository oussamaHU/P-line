package p2;

import java.sql.SQLException;
import java.util.List;

public interface ProductDAO {
    boolean save(Product product) throws SQLException;
    boolean update(Product product) throws SQLException;
    boolean delete(Product product) throws SQLException;
    List<Product> findByOvkaart(OVChipkaart ovChipkaart) throws SQLException;
    List<Product> findAll() throws SQLException;
}
