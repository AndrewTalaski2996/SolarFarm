package learn.solar.data;

import learn.solar.models.Panel;
import learn.solar.models.Material;

import java.util.List;

public interface PanelRepository {
    public List<Panel> findBySection(String section) throws DataException;

    public Panel add(Panel panel) throws DataException;

    public boolean update(Panel panel) throws DataException;

    public boolean deleteById(int id) throws DataException;
}
