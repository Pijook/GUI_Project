package com.company.sender;

import com.company.Main;
import com.company.menu.Menu;
import com.company.menu.Option;
import com.company.warehouse.StoredContainer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SenderController {

    private final List<Sender> senders;

    public SenderController(){
        senders = new ArrayList<>();
    }

    public Menu getSendersMenu(){
        Menu menu = new Menu("Senders");

        menu.addOption(1, new Option("Manage senders", () -> {
            menu.goToMenu(getManageSendersMenu());
        }, false));

        menu.addOption(2, new Option("Create new sender", () -> {
            createNewSender();
        }, false));

        return menu;
    }

    public Menu getManageSendersMenu(){
        Menu menu = new Menu("Select sender");

        int i = 1;
        for(Sender sender : senders){
            menu.addOption(i, new Option(sender.getName() + " " + sender.getSurname(), () -> {
                menu.goToMenu(sender.getSenderMenu());
            }, false));
            i++;
        }

        return menu;
    }

    public void createNewSender(){
        String name = null;
        String surname = null;
        String address = null;
        String mail = null;
        String userID = null;

        Pattern pattern;
        Matcher matcher;

        Scanner scanner = new Scanner(System.in);

        pattern = Pattern.compile("[A-Z][a-z]*");
        while(name == null){
            System.out.print("Name: ");
            name = scanner.nextLine();

            matcher = pattern.matcher(name);
            if(!matcher.find()){
                System.out.println("Invalid name!");
                name = null;
            }
        }

        while(surname == null){
            System.out.print("Surname: ");
            surname = scanner.nextLine();

            matcher = pattern.matcher(surname);
            if(!matcher.find()){
                System.out.println("Invalid surname!");
                surname = null;
            }
        }

        System.out.print("Address: ");
        address = scanner.nextLine();

        pattern = Pattern.compile("[a-z\\\\.]*[@][a-z]*([\\\\.][a-z]*)+");
        while(mail == null){
            System.out.print("E-mail: ");
            mail = scanner.nextLine();

            matcher = pattern.matcher(mail);

            if(!matcher.find()){
                System.out.println("Invalid e-mail!");
                mail = null;
            }
        }

        pattern = Pattern.compile("[0-9]{11}");
        while(userID == null){
            System.out.print("Pesel: ");
            userID = scanner.nextLine();

            matcher = pattern.matcher(userID);

            if(!matcher.find()){
                System.out.println("Invalid pesel!");
                userID = null;
            }
        }

        senders.add(new Sender(name, surname, address, mail, userID, new ArrayList<>()));
    }

    public void loadSenders() throws IOException {
        System.out.println("Loading senders...");
        File file = new File("senders.txt");

        if(!file.exists()){
            file.createNewFile();
        }

        Scanner scanner = new Scanner(file);

        Sender sender = null;
        while(scanner.hasNext()){
            String line = scanner.nextLine();
            if(line.contains("/")){
                String name = scanner.nextLine().split(" ")[1];
                String surname = scanner.nextLine().split(" ")[1];
                String[] addressLines = scanner.nextLine().split(" ");
                String address = "";
                for(int i = 1; i < addressLines.length; i++){
                    address += addressLines[i] + " ";
                }
                String mail = scanner.nextLine().split(" ")[1];
                String userID = scanner.nextLine().split(" ")[1];

                sender = new Sender(name, surname, address, mail, userID, new ArrayList<>());
                senders.add(sender);
            }
        }

        System.out.println("Loaded " + senders.size() + " senders!");
    }

    public void saveSenders() throws IOException {
        File file = new File("senders.txt");

        if(file.exists()){
            file.delete();
            file.createNewFile();
        }

        PrintWriter printer = new PrintWriter(new FileWriter(file));

        for(Sender sender : senders){
            printer.println("/");
            printer.println(sender.toString());
        }

        printer.close();
    }

    public Sender getSender(String userID){
        for(Sender sender : senders){
            if(sender.getUserID().equals(userID)){
                return sender;
            }
        }
        return null;
    }

    public void addSender(Sender sender){
        senders.add(sender);
    }

    public List<Sender> getSenders() {
        return senders;
    }
}
