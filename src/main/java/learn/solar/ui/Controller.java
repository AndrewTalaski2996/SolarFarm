package learn.solar.ui;

import learn.solar.data.DataException;
import learn.solar.domain.PanelResult;
import learn.solar.domain.PanelService;
import learn.solar.models.Panel;

public class Controller {
    private final View view;
    private final PanelService service;

    public Controller(View view, PanelService service) {
        this.view = view;
        this.service = service;
    }

    public void run() {
        view.printHeader("Welcome to the Solar Farm");
        try {
            runMenu();
        } catch (DataException e) {
            view.displayText("Something went wrong.");
            view.displayText(e.getMessage());
        }
        view.displayText("Goodbye!");
    }

    private void runMenu() throws DataException {
        boolean exit = false;

        view.printHeader("Main Menu");

        while (!exit) {
            int selection = view.chooseOptionFromMenu();
            switch (selection) {
                case 0:
                    exit = true;
                    break;
                case 1:
                    viewBySection();
                    break;
                case 2:
                    addPanel();
                    break;
                case 3:
                    updatePanel();
                    break;
                case 4:
                    deletePanel();
                    break;
                default:
                    view.displayText("This is not a valid choice.");
                    break;
            }
        }
    }

    private void viewBySection() throws DataException {
        //calls readSection on input
        view.printHeader("Find Panels by Section");
        String section = view.readSection("Section Name: ");
        view.printPanels(section, service.findAll());
    }

    private void addPanel() throws DataException {
        Panel panel = view.makePanel();

        PanelResult result = service.add(panel);

        view.printResult(result);

        if (result.isSuccess()) {
            view.displayText("Panel " + panel.getSection() + "-" + panel.getRow() + "-" + panel.getColumn() + " was added.");
        }
    }

    private void updatePanel() throws DataException {
        view.printHeader("Update a Panel");

        String section = view.readSection("Section: ");
        Panel panel = view.choosePanel(section, service.findAll());
        PanelResult isNull = new PanelResult();
        if (panel == null) {
            isNull.addMessage("Cannot update non-existent panel.");
            view.printResult(isNull);
        } else {
            PanelResult result = service.update(panel);

            if (result.isSuccess()) {
                Panel updated = view.update(panel);
                PanelResult updatedResult = service.update(updated);
                if (updatedResult.isSuccess()) {
                    view.printResult(updatedResult);
                    view.displayText("Panel " + updated.getSection() + "-" + updated.getRow() + "-" + updated.getColumn() + " was updated.");
                } else {
                    view.printResult(updatedResult);
                }
            } else {
                view.printResult(result);
            }
        }
    }

    private void deletePanel() throws DataException {
        view.printHeader("Remove a Panel");
        String section = view.readSection("Section: ");
        Panel toRemove = view.choosePanel(section, service.findAll());
        PanelResult result = new PanelResult();
        if (toRemove == null) {
            result.addMessage("Panel to remove does not exist.");
            view.printResult(result);
        } else {
            result = service.deleteById(toRemove.getId());
            view.printResult(result);

            if (result.isSuccess()) {
                view.displayText("Panel " + toRemove.getSection() + "-" + toRemove.getRow() + "-" + toRemove.getColumn() + " was removed.");
            }
        }
    }
}
