package learn.solar.ui;

import learn.solar.domain.PanelResult;
import learn.solar.models.Material;
import learn.solar.models.Panel;

import java.time.Year;
import java.util.List;
import java.util.Scanner;

public class View {
    private final Scanner console = new Scanner(System.in);

    public int chooseOptionFromMenu() {
        return readInt("0. Exit\n1. Find Panel by Section\n2. Add a Panel\n3. Update a Panel\n4. Remove a Panel\nSelect [0-4]: ", 4);
    }

    public void printHeader(String header) {
        System.out.println();
        System.out.println(header);
        System.out.println("=".repeat(header.length()));
    }

    public void printResult(PanelResult result) {
        if (result.isSuccess()) {
            displayText("[Success]");
        } else {
            displayText("[Err]");
            for (String message : result.getMessages()) {
                displayText(message);
            }
        }
    }

    public void printPanels(String sectionName, List<Panel> panels) {
        displayText("Panels in " + sectionName);
        displayText("Row\tCol\tYear\tMaterial\tTracking");
        for (Panel panel : panels) {
            if (panel.getSection().equals(sectionName)) {
                displayText(String.format("%s\t%s\t%s\t%s\t%s", panel.getRow(), panel.getColumn(),
                        panel.getInstallationYear(), panel.getMaterial(), panel.isTracking()));
            }
        }
    }

    public Panel choosePanel(String sectionName, List<Panel> panels) {
        int row = readInt("Enter a Row: ");
        int col = readInt("Enter a Column: ");

        for (Panel p : panels) {
            if (p.getSection().equalsIgnoreCase(sectionName) && p.getRow() == row && p.getColumn() == col) {
                return p;
            }
        }

        return null;
    }

    public Panel makePanel() {
        Panel panel = new Panel();
        panel.setSection(readRequiredString("Section: "));
        panel.setRow(readInt("Row: "));
        panel.setColumn(readInt("Column: "));
        panel.setInstallationYear(readInt("Installation year: "));
        panel.setMaterial(readMaterial("Material: "));
        String trackingOption = readRequiredString("Tracking [y/n]: ");
        if (trackingOption.equalsIgnoreCase("y")) {
            panel.setTracking(true);
        } else if (trackingOption.equalsIgnoreCase("n")) {
            panel.setTracking(false);
        }
        return panel;
    }

    public Panel update(Panel panel){
        displayText("Editing " + panel.getSection() + "-" + panel.getRow() + "-" + panel.getColumn());
        displayText("Press [Enter] to keep original value.");
        String section = readRequiredString("Section (" + panel.getSection() + "): ");
        if (!section.isBlank()) {
            panel.setSection(section);
        }
        int row = readInt("Row (" + panel.getRow() + "): ", 251);
        if (row != 0) {
            panel.setRow(row);
        }
        int col = readInt("Column (" + panel.getColumn() + "): ", 251);
        if (col != 0) {
            panel.setColumn(col);
        }
        Material material = readMaterial("Material (" + panel.getMaterial() + "): ");
        if (material != null) {
            panel.setMaterial(material);
        }
        int installYear = readInt("Installation Year (" + panel.getInstallationYear() + "): ");
        if (installYear != 0) {
            panel.setInstallationYear(installYear);

        }

        String track = readRequiredString("Tracked (" + panel.isTracking() + "): ");
        if (!track.isBlank()) {
            panel.setTracking(Boolean.parseBoolean(track));

        }
        return panel;
    }

    public void displayText(String line) {
        System.out.println();
        System.out.println(line);
    }

    public String readSection(String prompt) {
        displayText(prompt);
        return console.nextLine();
    }

    private String readRequiredString(String prompt) {
        displayText(prompt);
        String string = console.nextLine();
        if (string == null) {
            displayText("You must enter a value.");
            string = readRequiredString(prompt);
        }
        if (prompt.contains("Track") && (!string.equalsIgnoreCase("Y") && !string.equalsIgnoreCase("N"))) {
            displayText("Please choose either Y for yes, or N for no.");
            string = readRequiredString(prompt);
        }
        return string;
    }

    private int readInt(String prompt) {
        while (true) {
            displayText(prompt);
            String intValue = console.nextLine();
            if (intValue.isBlank()) {
                return 0;
            }
            try {
                int val = Integer.parseInt(intValue);
                if (val > Year.now().getValue()) {
                    displayText("[Err]");
                    displayText("Value must be in the past.");
                } else if (val < 0) {
                    displayText("[Err]");
                    displayText("Value cannot be negative.");
                } else {
                    return val;
                }
            } catch (NumberFormatException e) {
                System.out.printf("%s is not a valid number.%n", intValue);
            }
        }
    }

    private int readInt(String prompt, int max) {
        while (true) {
            displayText(prompt);
            String string = console.nextLine();
            if (string.isBlank()) {
                return 0;
            }
            try {
                int intVal = Integer.parseInt(string);
                if (intVal < 0 || intVal > max) {
                    if (prompt.contains("Select")) {
                        displayText("[Err]");
                        displayText("Please choose a valid option.");
                    } else {
                        displayText("[Err]");
                        displayText("Value must be between 1 and 250.");
                    }
                } else {
                    return intVal;
                }
            } catch (NumberFormatException e) {
                System.out.printf("%s is not a valid number.%n", string);
            }
        }
    }

    private Material readMaterial(String prompt) {
        while(true) {
            displayText(prompt);
            displayText("Suitable materials are: MULTISI, MONOSI, AMORSI, CDTE, or CIGS");
            String mat = console.nextLine();
            if (mat.isBlank()) {
                return null;
            }
            try {
                return Material.valueOf(mat);
            } catch (IllegalArgumentException e) {
                System.out.printf("%s is not a Material.%n", mat);
            }
        }
    }
}
