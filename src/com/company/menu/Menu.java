package com.company.menu;

import java.util.HashMap;
import java.util.Scanner;

public class Menu {

    private HashMap<Integer, Option> options;

    public Menu(){
        options = new HashMap<>();
    }

    public void addOption(Integer optionNumber, Option option){
        options.put(optionNumber, option);
    }

    public void openMenu(){
        Scanner scanner = new Scanner(System.in);

        int selectedOption = -1;
        while(selectedOption != 0){
            for(int number : options.keySet()){
                System.out.println(number + ". " + options.get(number).getOptionName());
            }
            System.out.println("0. Exit");
            System.out.print("Choose number: ");

            selectedOption = scanner.nextInt();

            if(options.containsKey(selectedOption)){
                options.get(selectedOption).getRunnable().run();
            }
        }
    }

}
