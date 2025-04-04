package learn.solar.ui;

import learn.solar.domain.PanelResult;
import learn.solar.models.Material;
import learn.solar.models.Panel;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class View {
    private final Scanner console = new Scanner(System.in);

    public int chooseOptionFromMenu() {
        displayText("0. Exit");
        displayText("1. Find Panel by Section");
        displayText("2. Add a Panel");
        displayText("3. Update a Panel");
        displayText("4. Remove a Panel");
        return readInt("Select [0-4]: ", 0, 4);
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
        System.out.println("Panels in " + sectionName);
        System.out.println("Row\tCol\tYear\tMaterial\tTracking");
        for (Panel panel : panels) {
            if (panel.getSection().equals(sectionName)) {
                displayText(String.format("%s\t%s\t%s\t%s\t%s", panel.getRow(), panel.getColumn(),
                        panel.getInstallationYear(), panel.getMaterial(), panel.isTracking()));
            }
        }
    }

    public Panel choosePanel(String sectionName, List<Panel> panels) {
        System.out.println("Section: " + sectionName);
        System.out.print("Enter Row: ");
        int row = readInt(console.nextLine());
        System.out.println();
        System.out.print("Enter a Column: ");
        int col = readInt(console.nextLine());
        System.out.println();

        for (Panel p : panels) {
            if (Objects.equals(p.getSection(), sectionName) && p.getRow() == row && p.getColumn() == col) {
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
        panel.setMaterial(readMaterial());
        panel.setTracking(Boolean.parseBoolean(readRequiredString("Tracking [y/n]: ")));

        return panel;
    }

    public Panel update(Panel panel){
        System.out.println("Editing " + panel.getSection() + "-" + panel.getRow() + "-" + panel.getColumn());
        System.out.println("Press [Enter] to keep original value.");
        panel.setSection(readRequiredString("Section (" + panel.getSection() + "): "));
        panel.setRow(readInt("Row (" + panel.getRow() + "): ", 1, 250));
        panel.setColumn(readInt("Column (" + panel.getColumn() + "): ", 1, 250));
        System.out.print("Material (" + panel.getMaterial() + "): ");
        panel.setMaterial(readMaterial());
        System.out.println();
        panel.setInstallationYear(readInt("Installation Year (" + panel.getInstallationYear() + "): "));
        panel.setTracking(Boolean.parseBoolean(readRequiredString("Tracked (" + panel.isTracking() + "): ")));

        return panel;
    }

    public void displayText(String line) {
        System.out.println();
        System.out.println(line);
    }

    public String readSection(String sectionName) {
        System.out.println("Section: ");
        return console.nextLine();
    }

    private String readRequiredString(String prompt) {
        displayText(prompt);
        String string = console.nextLine();
        if (string == null || string.isBlank()) {
            displayText("You must enter a value.");
            string = readRequiredString(prompt);
        }
        return string;
    }

    private int readInt(String prompt) {
        displayText(prompt);
        return Integer.parseInt(console.nextLine());
    }

    private int readInt(String prompt, int min, int max) {
        displayText(prompt);
        while (true) {
            String string = console.nextLine();
            try {
                int intVal = Integer.parseInt(string);
                if (intVal < min || intVal > max) {
                    System.out.println("[Err]");
                    System.out.println("Value must be between 1 and 250.");
                } else {
                    return intVal;
                }
            } catch (NumberFormatException e) {
                System.out.printf("%s is not a valid number.%n", string);
            }
        }
    }

    private Material readMaterial() {
        while(true) {
            String mat = console.nextLine();
             mat = mat.toUpperCase();
            try {
                return Material.valueOf(mat);
            } catch (IllegalArgumentException e) {
                System.out.printf("%s is not a Material.%n", mat);
            }
        }
    }
}
