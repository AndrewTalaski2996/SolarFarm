package learn.solar.data;

import learn.solar.models.Panel;

import java.util.Collections;
import java.util.List;

public class PanelFileRepository implements PanelRepository {
    private static final String DELIMITER = ",";
    private static final String DELIMITER_REPLACEMENT = "@@@";
    private static final String HEADER = "id,section,row,column,installationDate,material,tracking";
    private final String filePath;

    public PanelFileRepository(String filePath) {
        this.filePath = filePath;
    }

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

    //Helper methods
    private int getNextId(List<Panel> panels) {
        return 0;
    }

    private void writeAll(List<Panel> panels) throws DataException {

    }

    private String clean(String value) {
        return null;
    }

    private String restore(String value) {
        return null;
    }

    private String serialize(Panel panel) {

        return "";
    }

    private Panel deserialize(String panel) {

        return null;
    }
}
