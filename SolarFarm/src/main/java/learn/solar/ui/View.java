package learn.solar.ui;

import learn.solar.domain.PanelResult;
import learn.solar.models.Material;
import learn.solar.models.Panel;

import java.util.List;
import java.util.Scanner;

public class View {
    private Scanner console = new Scanner(System.in);

    public int chooseOptionFromMenu() {
        return 0;
    }

    public void printHeader(String header) {

    }

    public void printResult(PanelResult result) {

    }

    public void printPanels(String sectionName, List<Panel> panels) {

    }

    public void choosePanel(String sectionName, List<Panel> panels) {

    }

    public Panel makePanel() {
        return null;
    }

    public Panel update(Panel panel){
        return null;
    }

    public String readSection(String sectionName) {
        return null;
    }

    private String readRequiredString(String string) {
        return null;
    }

    private int readInt(String string) {
        return 0;
    }

    private int readInt(String string, int min, int max) {
        return 0;
    }

    private Material readMaterial() {
        return null;
    }
}
