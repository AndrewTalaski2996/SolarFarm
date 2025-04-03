package learn.solar.data;

import learn.solar.models.Panel;

import java.util.List;

public interface PanelRepository {
    public List<Panel> findBySection(String section);

    public Panel add(Panel panel);

    public boolean update(Panel panel);

    public boolean deleteById(int id);
}
