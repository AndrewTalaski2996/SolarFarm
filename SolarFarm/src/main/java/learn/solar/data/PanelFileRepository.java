package learn.solar.data;

import learn.solar.models.Panel;

import java.util.Collections;
import java.util.List;

public class PanelFileRepository implements PanelRepository {

    private String filePath;

    @Override
    public List<Panel> findBySection(String section) {
        return Collections.emptyList();
    }

    @Override
    public Panel add(Panel panel) {
        return null;
    }

    @Override
    public boolean update(Panel panel) {
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }

    private List<Panel> findAll() {

        return Collections.emptyList();
    }

    private String serialize(Panel panel) {

        return "";
    }

    private Panel deserialize(String panel) {

        return null;
    }
}
