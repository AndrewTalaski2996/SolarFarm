package learn.solar.ui;

import learn.solar.data.DataException;
import learn.solar.domain.PanelResult;
import learn.solar.domain.PanelService;
import learn.solar.models.Panel;

import javax.xml.crypto.Data;
import java.util.List;

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
            }
        }
    }

    private void viewBySection() throws DataException {
        //calls readSection on input
    }

    private void addPanel() throws DataException {
        Panel panel = view.makePanel();

        PanelResult result = service.add(panel);

        if (result.isSuccess()) {
            view.displayText("Panel was successfully added.");
        } else {
            //display errors
        }
    }

    private void updatePanel() throws DataException {
        view.printHeader("Update a Panel");
        //call readSection
        //call view.choosePanel pass in readSection and service.findAll()
        //call view.update on previous result
    }

    private void deletePanel() throws DataException {
        //calls readSection
    }
}
