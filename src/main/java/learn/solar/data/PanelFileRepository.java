package learn.solar.data;

import learn.solar.models.Material;
import learn.solar.models.Panel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PanelFileRepository implements PanelRepository {
    private static final String DELIMITER = ",";
    private static final String DELIMITER_REPLACEMENT = "@@@";
    private static final String HEADER = "id,section,row,column,installationYear,material,tracking";
    private final String filePath;

    public PanelFileRepository(String filePath) {
        this.filePath = filePath;
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
        List<Panel> all = findAll();
        panel.setId(getNextId(all));
        all.add(panel);
        writeToFile(all);
        return panel;
    }

    @Override
    public boolean update(Panel panel) throws DataException {
        List<Panel> all = findAll();
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getId() == panel.getId()) {
                all.set(i, panel);
                writeToFile(all);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteById(int id) throws DataException {
        List<Panel> all = findAll();
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getId() == id) {
                all.remove(i);
                writeToFile(all);
                return true;
            }
        }
        return false;
    }

    public List<Panel> findAll() throws DataException {
        List<Panel> all = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            reader.readLine(); //ignore header
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                Panel panel = deserialize(line);
                if (panel != null) {
                    all.add(panel);
                }
            }
        } catch (FileNotFoundException ignored) {
        } catch (IOException e) {
            throw new DataException("Could not open file path: " + filePath);
        }
        return all;
    }

    //Helper methods
    private int getNextId(List<Panel> panels) {
        int nextId = 0;
        for (Panel panel : panels) {
            nextId = Math.max(nextId, panel.getId());
        }
        return nextId + 1;
    }

    private void writeToFile(List<Panel> panels) throws DataException {
        try (PrintWriter writer = new PrintWriter(filePath)) {
            writer.println(HEADER);
            for (Panel panel : panels) {
                writer.println(serialize(panel));
            }
        } catch (IOException e) {
            throw new DataException("Could not write to file path " + filePath);
        }
    }

    private String clean(String value) {
        return value.replace(DELIMITER, DELIMITER_REPLACEMENT);
    }

    private String restore(String value) {
        return value.replace(DELIMITER_REPLACEMENT, DELIMITER);
    }

    private Panel deserialize(String line) {
        String[] fields = line.split(DELIMITER);
        if (fields.length != 7) {
            return null;
        }
        return new Panel(Integer.parseInt(fields[0]), restore(fields[1]), Integer.parseInt(fields[2]),
                Integer.parseInt(fields[3]), Integer.parseInt(fields[4]), Material.valueOf(fields[5]), Boolean.parseBoolean(fields[6]));
    }

    private String serialize(Panel panel) {
        StringBuilder buffer = new StringBuilder(100);
        buffer.append(panel.getId()).append(DELIMITER);
        buffer.append(clean(panel.getSection())).append(DELIMITER);
        buffer.append(panel.getRow()).append(DELIMITER);
        buffer.append(panel.getColumn()).append(DELIMITER);
        buffer.append(panel.getInstallationYear()).append(DELIMITER);
        buffer.append(panel.getMaterial()).append(DELIMITER);
        buffer.append(panel.isTracking());

        return buffer.toString();
    }
}
