package com.company.menu;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Menu {

    private String menuTitle;
    private HashMap<Integer, Option> options;
    private boolean subMenu;

    public Menu(String menuTitle){
        this.menuTitle = menuTitle;
        options = new HashMap<>();
        this.subMenu = true;
    }

    public Menu(String menuTitle, boolean subMenu){
        this.menuTitle = menuTitle;
        options = new HashMap<>();
        this.subMenu = subMenu;
    }

    public void addOption(Integer optionNumber, Option option){
        options.put(optionNumber, option);
    }

    public void open(){
        Scanner scanner = new Scanner(System.in);

        int selectedOption = -1;
        while(selectedOption != 0){
            System.out.println("");
            System.out.println("----------------------");
            System.out.println("   " + menuTitle + "   ");
            System.out.println("----------------------");
            System.out.println("");
            for(int number : options.keySet()){
                System.out.println(number + ". " + options.get(number).getOptionName());
            }
            if(subMenu){
                System.out.println("0. Back");
            }
            else{
                System.out.println("0. Exit");
            }
            System.out.println("");
            System.out.print("Choose option: ");


            try{
                String text = scanner.nextLine();
                selectedOption = Integer.parseInt(text);
            }
            catch (NumberFormatException e){
                System.out.println("Enter valid option!");
                continue;
            }

            clearScreen();
            if(options.containsKey(selectedOption)){
                Option option = options.get(selectedOption);
                option.getRunnable().run();
                if(option.isReturnAfterAction()){
                    selectedOption = 0;
                }
            }
        }
    }

    private void clearScreen(){
        try {
            /*
                Resource https://stackoverflow.com/questions/2979383/how-to-clear-the-console
             */
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isSubMenu() {
        return subMenu;
    }

    public void setSubMenu(boolean subMenu) {
        this.subMenu = subMenu;
    }
}
