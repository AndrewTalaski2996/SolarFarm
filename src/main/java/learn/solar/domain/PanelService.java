package learn.solar.domain;

import learn.solar.data.PanelRepository;
import learn.solar.models.Panel;

import java.util.List;

public class PanelService {
    private PanelRepository repository;

    public PanelService(PanelRepository repository) {
        this.repository = repository;
    }

    public List<String> findBySection(String section) {

        return java.util.Collections.emptyList();
    }

    public PanelResult add(Panel panel) {
        return null;
    }

    public PanelResult update(Panel panel) {
        return null;
    }

    public PanelResult deleteById(int panelId) {
        return null;
    }

    private PanelResult validate(Panel panel) {
        return null;
    }
}
