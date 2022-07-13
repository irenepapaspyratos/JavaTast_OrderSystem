package model;

import java.util.Scanner;

public class Questionary {

    Scanner terminalScanner = new Scanner(System.in);
    int returnValue;

    public int askingType(String type) {

        switch (type) {

            case "mainAction": {
                System.out.println("What would you like to do? \n" +
                        "1 = Show all products \n" +
                        "2 = Edit the product list (add or delete products) \n" +
                        "3 = Show details of a single product \n" +
                        "4 = Show all orders \n" +
                        "5 = Edit the order list (add or delete orders) \n" +
                        "6 = Show details of a single order \n" +
                        "7 = Edit a specific order (add, delete or adjust amount of products) \n" +
                        "8 = Quit \n" +
                        "Your choice: ");

                String choice = terminalScanner.nextLine();
                returnValue = Integer.parseInt(checkAnswer(choice, "mainAction"));
            }
            break;

            case "moreAction": {
                System.out.println("Would you like to do anything else? \n" +
                        "1 = Yes, back to Start! \n" +
                        "2 = No, quit now. \n" +
                        "Your choice: ");
                String choice = terminalScanner.nextLine();

                while (checkAnswer(choice, "moreAction").equals("666")) {
                    printText(null, "noMatch");
                    askingType("moreAction");
                    choice = terminalScanner.nextLine();
                }

                if (choice.equals("2")) printText(null, "quit");
                returnValue = Integer.parseInt(choice);
            }
            break;

            case "editAction": {
                System.out.println("What would you like to do? \n" +
                        "1 = Add \n" +
                        "2 = Delete \n" +
                        "3 = Back to Start \n" +
                        "Your choice: ");
                String choice = terminalScanner.nextLine();

                while (checkAnswer(choice, "editAction").equals("666")) {
                    printText(null, "noMatch");
                    askingType("editAction");
                    choice = terminalScanner.nextLine();
                }

                returnValue = Integer.parseInt(choice);
            }
            break;

            case "idAction": {
                System.out.println("What would you like to do? \n" +
                        "1 = Enter your id manually \n" +
                        "2 = Let your id be generated automatically \n" +
                        "3 = Back to Start \n" +
                        "Your choice: ");
                String choice = terminalScanner.nextLine();

                while (checkAnswer(choice, "idAction").equals("666")) {
                    printText(null, "noMatch");
                    askingType("idAction");
                    choice = terminalScanner.nextLine();
                }

                returnValue = Integer.parseInt(choice);
            }
            break;

            case "manualIdAction": {
                Scanner terminalScanner = new Scanner(System.in);
                System.out.println("The id of your new item must be: \n" +
                        "numeric, not empty, not 0, not already existing. \n" +
                        "Enter your id or quit with 0: ");
                String id = terminalScanner.nextLine();

                while (id.equals(" ") || id.equals("") || !id.matches("\\d*")) {
                    printText(null, "noMatch");
                    id = terminalScanner.nextLine();
                }

                return Integer.parseInt(id);
            }

        }

        return returnValue;
    }


    public String checkAnswer(String choice, String type) {

        String checkAnswerValue = "666";

        switch (type) {

            case "mainAction":
                if (choice.matches("[1-8]")) {
                    printText(choice, "letsGo");
                    checkAnswerValue = choice;
                }
                break;

            case "moreAction":
                if (choice.matches("[1-2]")) {
                    printText(choice, "letsGo");
                    checkAnswerValue = choice;
                }
                break;

            case "editAction":
            case "idAction":
                if (choice.matches("[1-3]")) {
                    printText(choice, "letsGo");
                    checkAnswerValue = choice;
                }
                break;

        }

        return checkAnswerValue;
    }


    public void printText(String choice, String info) {

        switch (info) {
            case "empty":
                System.out.println("Your " + choice + " is empty.");
                break;
            case "letsGo":
                System.out.println("You selected option " + choice + " - Let's go!");
                break;
            case "noMatch":
                System.out.println("Your choice does not match the given options.");
                break;
            case "quit":
                System.out.println("You left the program.");
                break;
        }

    }
}


