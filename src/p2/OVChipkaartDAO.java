package p2;

import java.sql.SQLException;
import java.util.List;

public interface OVChipkaartDAO {
    public boolean save (OVChipkaart ovChipkaart) throws SQLException;
    public boolean update(OVChipkaart ovChipkaart) throws SQLException;
    public boolean delete(OVChipkaart ovChipkaart);
    public List<OVChipkaart>findByReiziger(Reiziger reiziger);
    public OVChipkaart findById(int kaartnummer );
    public List<OVChipkaart> findAll() throws SQLException;

}
