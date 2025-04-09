package learn.solar.data;

import learn.solar.models.Material;
import learn.solar.models.Panel;

import java.util.ArrayList;
import java.util.List;

public class PanelRepositoryDouble implements PanelRepository{

    @Override
    public List<Panel> findAll() {
        List<Panel> panels = new ArrayList<>();
        panels.add(new Panel(1,"Main",3,4,2017, Material.CDTE, false));
        panels.add(new Panel(2, "Lower Hill", 2, 3, 2013, Material.MONOSI, false));
        panels.add(new Panel(3, "Upper Hill", 1, 1, 2024, Material.CIGS, true));

        return panels;
    }

    @Override
    public List<Panel> findBySection(String section) throws DataException {
        List<Panel> panels = new ArrayList<>();
        for (Panel panel : findAll()) {
            if (panel.getSection().equals(section)) {
                panels.add(panel);
            }
        }
        return panels;
    }

    @Override
    public Panel add(Panel panel) throws DataException {
        panel.setId(4);
        return panel;
    }

    @Override
    public boolean update(Panel panel) throws DataException {
        return panel.getId() > 0;
    }

    @Override
    public boolean deleteById(int id) throws DataException {
        return id != 9999;
    }
}
