package com.company.sender;

import com.company.menu.Menu;
import com.company.menu.Option;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sender {

    private String name;
    private String surname;
    private String address;
    private String mail;
    private String userID;
    private Integer warnings;

    public Sender(String name, String surname, String address, String mail, String userID, Integer warnings) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.mail = mail;
        this.userID = userID;
        this.warnings = warnings;
    }

    @Override
    public String toString() {
        return "name: " + name + "\n" +
                "surname: " + surname + "\n" +
                "address: " + address + "\n" +
                "mail: " + mail + "\n" +
                "userID: " + userID + "\n" +
                "warnings: " + warnings;
    }

    public void openSenderMenu(){
        Menu menu = new Menu(name + " " + surname);

        menu.addOption(1, new Option("Show info", () -> {
            System.out.println(this + "\n" + "birthDate: " + getBirthDate());
        }, false));

        menu.addOption(2, new Option("Change address", () -> {
            Scanner scanner = new Scanner(System.in);
            Matcher matcher;
            Pattern pattern = Pattern.compile("((\\d)?\\s\\p{L}*)\\s(.*)");
            address = null;
            while(address == null){
                System.out.print("Address: ");
                address = scanner.nextLine();

                matcher = pattern.matcher(address);
                if(!matcher.find()){
                    System.out.println("Invalid address!");
                    address = null;
                }
            }
        }, false));

        menu.addOption(3, new Option("Change e-mail", () -> {

            Scanner scanner = new Scanner(System.in);
            Matcher matcher;
            Pattern pattern = Pattern.compile("^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$");
            mail = null;
            while(mail == null){
                System.out.print("E-mail: ");
                mail = scanner.nextLine();

                matcher = pattern.matcher(mail);
                if(!matcher.find()){
                    System.out.println("Invalid mail!");
                    address = null;
                }
            }

        }, false));

        menu.open();
    }

    public LocalDate getBirthDate(){
        int year = Integer.parseInt(userID.substring(0, 2));
        int month = Integer.parseInt(userID.substring(2, 4));
        String monthText = "";
        int day = Integer.parseInt(userID.substring(4,6));
        String dayText = "";

        if(month >= 81 && month <= 92){
            year += 1800;
            month -= 80;
        }
        else if(month >= 1 && month <= 12){
            year += 1900;
        }
        else if(month >= 21 && month <= 32){
            year += 2000;
            month -= 20;
        }
        else if(month >= 41 && month <= 52){
            year += 2100;
            month -= 40;
        }
        else if(month >= 61 && month <= 72){
            year += 2200;
            month -= 60;
        }

        if(month < 10){
            monthText = "0" + month;
        }
        else{
            monthText = String.valueOf(month);
        }
        if(day < 10){
            dayText = "0" + day;
        }
        else{
            dayText = String.valueOf(day);
        }

        return  LocalDate.parse(year + "-" + monthText + "-" + dayText);
    }

    public void increaseWarnings(int amount){
        this.warnings += amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Integer getWarnings() {
        return warnings;
    }

    public void setWarnings(Integer warnings) {
        this.warnings = warnings;
    }
}
