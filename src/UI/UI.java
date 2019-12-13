package UI;

import LOGIC.DetergentType;
import LOGIC.MainProgram;
import LOGIC.NotEnoughResourceException;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("SpellCheckingInspection")
public class UI {
    private MainProgram Controller;
    private Scanner scan;

    public UI() {
        Controller = new MainProgram();
        scan = new Scanner(System.in);

        //uzywane tylko do testowania
        //Controller.maxFill();
        Controller.setResources(50, 100, 100, 100);
    }

    public void StartWashingMachine() {
        System.out.println("WITAJ!");

        boolean correct = true;
        int choice;
        while (correct) {
            try {
                printOptions();
                choice = scan.nextInt();
            } catch (InputMismatchException e) {
                choice = 0;
            }
            switch (choice) {
                case 1:
                    wash();
                    break;
                case 2:
                    addWashingProgram();
                    break;
                case 3:
                    addDryingProgram();
                    break;
                case 4:
                    checkResourceLevel();
                    break;
                case 5:
                    updateResourceLevel();
                    break;
                case 6:
                    deleteWashingProgram();
                    break;
                case 7:
                    deleteDryingProgram();
                    break;
                case 8:
                    TURN_OFF();
                    correct = false;
                default:
                    System.out.println("Niepoprawne wprowadzenie! \n");
            }
        }


    }

    private void addWashingProgram() {
        try {
            System.out.println("Name :");
            String name = scan.next();
            System.out.println("Time(s): ");
            int time = scan.nextInt();
            System.out.println("Detergent Volume(ml): ");
            int detergentVolume = scan.nextInt();
            System.out.println("Softener Volume(ml): ");
            int softenerVolume = scan.nextInt();
            Controller.addWashingProgram(name, time, detergentVolume, softenerVolume);
            Controller.saveProgrammes();

        } catch (InputMismatchException e) {
            System.out.println("ERROR");
        }

    }

    private void addDryingProgram() {
        try {
            System.out.println("Name:");
            String name = scan.next();
            System.out.println("Time(s): ");
            int time = scan.nextInt();
            System.out.println("Scent Volume(ml): ");
            int scentVolume = scan.nextInt();
            System.out.println("Temperature(°C): ");
            int temperature = scan.nextInt();
            Controller.addDryingProgram(name, time, scentVolume, temperature);
            Controller.saveProgrammes();

        } catch (InputMismatchException e) {
            System.out.println("ERROR");
        }
    }

    private void checkResourceLevel() {
        System.out.println("Detergent: " + Controller.currentDetergentLevel());
        System.out.println("Liquid detergent: " + Controller.currentLiquidDetergentLevel());
        System.out.println("Softener: " + Controller.currentSoftenerLevel());
        System.out.println("Scent: " + Controller.currentScentLevel());
    }

    //to powinno uzywac hardwarowego czujnika
    private void updateResourceLevel() {
        Controller.maxFill();
    }

    private void deleteWashingProgram() {
        printWashingOptions();
        int i = scan.nextInt();
        Controller.getWashingProgrammes().remove(i - 1);
        Controller.saveProgrammes();

    }

    private void deleteDryingProgram() {
        printDryingOptions();
        int i = scan.nextInt();
        Controller.getDryingProgrammes().remove(i - 1);
        Controller.saveProgrammes();

    }

    private void TURN_OFF() {
        scan.close();
        Controller.saveProgrammes();
        System.out.println("Do następnego! :)");
    }

    private void printOptions() {
        System.out.println("WYBIERZ DZIALANIE:\n");
        System.out.println("1. Pranie i suszenie\n");
        System.out.println("2. Dodaj nowy program prania\n");
        System.out.println("3. Dodaj nowy program suszenia\n");
        System.out.println("4. Sprawdz poziom plynow\n");
        System.out.println("5. Zaktualizuj poziom plynow\n");
        System.out.println("6. Usun program prania\n");
        System.out.println("7. Usun program suszenia\n");
        System.out.println("8. Wyjdz");
    }

    private void wash() {
        int checkWashingRange = Controller.getWashingProgrammes().size();
        int checkDryingRange = Controller.getDryingProgrammes().size();
        DetergentType type = DetergentType.NONE;
        int dry;

        int wash = getWashingOption() - 1;
        if (wash < checkWashingRange) {
            type = getDetergentType();
        }
        dry = getDryingOption() - 1;

        System.out.println(wash);
        System.out.println(type);
        System.out.println(dry);

        //Wywolanie start washing dla odpowiednich parametrow
        try {
            if (wash < checkWashingRange) {
                if (dry < checkDryingRange) {
                    //Pranie+suszenie
                    System.out.println("PRANIE + SUSZENIE");
                    Controller.StartWashing(Controller.getWashingProgrammes().get(wash), type, Controller.getDryingProgrammes().get(dry));
                } else {
                    //Pranie
                    System.out.println("PRANIE");
                    Controller.StartWashing(Controller.getWashingProgrammes().get(wash), type, null);
                }
            } else if (dry < checkDryingRange) {
                //Suszenie
                System.out.println("SUSZENIE");
                Controller.StartWashing(null, type, Controller.getDryingProgrammes().get(dry));
            }
        } catch (NotEnoughResourceException e) {
            System.out.println(e.getMessage() + "Uzupełnij pojemniki lub wybierz inny program!");
        }

        //Odliczanie do konca
        while (Controller.getTotalWashingTime() > 0) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Pozostało: " + Controller.getTotalWashingTime() + "s");
            Controller.decreaseTime();
        }
    }

    private int getWashingOption() {
        int choice;
        int i = Controller.getWashingProgrammes().size();
        do {
            System.out.println("Wybierz sposób prania: \n");
            printWashingOptions();
            System.out.println(i + 1 + ". Dalej \n");

            choice = scan.nextInt();
            System.out.println("Twój wybor to: " + choice);
        } while (choice <= 0 || choice > i + 1);
        return choice;
    }

    private void printWashingOptions() {
        int i;
        for (i = 1; i <= Controller.getWashingProgrammes().size(); i++) {
            System.out.println(i + ". " + Controller.getWashingProgrammes().get(i - 1) + "\n");
        }
    }

    private DetergentType getDetergentType() {
        int choice;
        do {
            System.out.println("Wybierz typ środka do prania: \n");
            System.out.println("1. Proszek \n");
            System.out.println("2. Płyn do prania: \n");
            choice = scan.nextInt();
        } while (choice <= 0 || choice > 2);
        return choice == 1 ? DetergentType.POWDER : DetergentType.LIQUID;
    }

    private int getDryingOption() {
        int choice;
        int i = Controller.getDryingProgrammes().size();
        do {
            System.out.println("Wybierz sposób suszenia: \n");
            printDryingOptions();
            System.out.println(i + 1 + ". Brak \n");

            choice = scan.nextInt();
            System.out.println("Twój wybor to: " + choice);

        } while (choice <= 0 || choice > i + 1);
        return choice;
    }

    private void printDryingOptions() {
        int i;
        for (i = 1; i <= Controller.getDryingProgrammes().size(); i++) {
            System.out.println(i + ". " + Controller.getDryingProgrammes().get(i - 1) + "\n");
        }
    }

}
