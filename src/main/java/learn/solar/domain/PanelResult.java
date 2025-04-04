package learn.solar.domain;

import learn.solar.models.Panel;

import java.util.ArrayList;
import java.util.List;

public class PanelResult {
    private ArrayList<String> messages = new ArrayList<>();
    private Panel panel;

    public boolean isSuccess() {
        return messages.isEmpty();
    }

    public List<String> getMessages() {
        return messages;
    }

    public Panel getPanel() {
        return panel;
    }

    public void setPanel(Panel panel) {
        this.panel = panel;
    }

    public void addMessage(String message) {
        messages.add(message);
    }
}
