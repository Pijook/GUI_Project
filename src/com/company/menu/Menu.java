package com.company.menu;

import java.util.HashMap;
import java.util.Scanner;

public class Menu {

    private String menuTitle;
    private HashMap<Integer, Option> options;

    public Menu(String menuTitle){
        this.menuTitle = menuTitle;
        options = new HashMap<>();
    }

    public void addOption(Integer optionNumber, Option option){
        options.put(optionNumber, option);
    }

    public void open(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("----------------------");
        System.out.println(menuTitle);
        System.out.println("----------------------");

        int selectedOption = -1;
        while(selectedOption != 0){
            for(int number : options.keySet()){
                System.out.println(number + ". " + options.get(number).getOptionName());
            }
            System.out.println("0. Exit");
            System.out.print("Choose option: ");

            selectedOption = scanner.nextInt();

            if(options.containsKey(selectedOption)){
                Option option = options.get(selectedOption);
                option.getRunnable().run();
                if(option.isReturnAfterAction()){
                    selectedOption = 0;
                }
            }
        }
    }

}
