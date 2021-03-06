package com.company.menu;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Menu {

    private String menuTitle;
    private HashMap<Integer, Option> options;
    private boolean subMenu;
    private int selectedOption;

    public Menu(String menuTitle){
        this.menuTitle = menuTitle;
        this.options = new HashMap<>();
        this.subMenu = true;
        this.selectedOption = -2;
    }

    public Menu(String menuTitle, boolean subMenu){
        this.menuTitle = menuTitle;
        this.options = new HashMap<>();
        this.subMenu = subMenu;
        this.selectedOption = -2;
    }

    public void addOption(Integer optionNumber, Option option){
        options.put(optionNumber, option);
    }

    public int open(){
        Scanner scanner = new Scanner(System.in);

        while(selectedOption != 0 && selectedOption != -1){
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
                System.out.println("-1. Exit");
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
        return selectedOption;
    }

    public void goToMenu(Menu menu){
        if(menu != null){
            int result = menu.open();
            if(result == -1){
                options.get(selectedOption).setReturnAfterAction(false);
                selectedOption = result;
            }
        }
    }

    private void clearScreen(){
        for(int i = 0; i < 60; i++){
            System.out.println("");
        }
    }

    public boolean isSubMenu() {
        return subMenu;
    }

    public void setSubMenu(boolean subMenu) {
        this.subMenu = subMenu;
    }
}
