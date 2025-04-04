package learn.solar.domain;

import learn.solar.data.DataException;
import learn.solar.data.PanelRepository;
import learn.solar.models.Panel;

import java.time.Year;
import java.util.List;

public class PanelService {
    private final PanelRepository repository;

    public PanelService(PanelRepository repository) {
        this.repository = repository;
    }

    public List<Panel> findAll() throws DataException {
        return repository.findAll();
    }

    public List<Panel> findBySection(String section) throws DataException {
        return repository.findBySection(section);
    }

    public PanelResult add(Panel panel) throws DataException {
        PanelResult result = validate(panel);
        if (!result.isSuccess()) {
            return result;
        }
        if (panel == null) {
            result.addMessage("Panel cannot be null.");
            return result;
        }
        if (panel.getId() > 0) {
            result.addMessage("Cannot create an existing panel.");
            return result;
        }

        for (Panel p : repository.findBySection(panel.getSection())) {
            if (p.getRow() == panel.getRow() && p.getColumn() == panel.getColumn()) {
                result.addMessage("Cannot create an existing panel.");
                return result;
            }
        }
        panel = repository.add(panel);
        result.setPanel(panel);
        return result;
    }

    public PanelResult update(Panel panel) throws DataException {
        PanelResult result = validate(panel);
        if (!result.isSuccess()) {
            return result;
        }
        boolean updated = repository.update(panel);
        if (!updated) {
            result.addMessage("Panel does not exist.");
        }
        return result;
    }

    public PanelResult deleteById(int panelId) throws DataException {
        PanelResult result = new PanelResult();
        if (!repository.deleteById(panelId)) {
            result.addMessage("Panel does not exist.");
        }
        return result;
    }

    private PanelResult validate(Panel panel) {
        PanelResult result = new PanelResult();

        if (panel == null) {
            result.addMessage("Panel cannot be null.");
            return result;
        }
        if (panel.getSection() == null || panel.getSection().isBlank()) {
            result.addMessage("Section is required.");
            return result;
        }
        if (panel.getRow() < 1 || panel.getRow() > 250) {
            result.addMessage("Row must be a positive value less than 250.");
            return result;
        }
        if (panel.getColumn() < 1 || panel.getColumn() > 250) {
            result.addMessage("Column must be a positive value less than 250.");
            return result;
        }
        if (panel.getInstallationYear() >= Year.now().getValue()) {
            result.addMessage("Installation Year must be a date before " + Year.now().getValue());
            return result;
        }
        if (panel.getMaterial() == null) {
            result.addMessage("Material is required.");
            return result;
        }

        return result;
    }
}
