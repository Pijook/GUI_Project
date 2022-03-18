package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Scanner;

public class PortTime extends Thread {

    private LocalDate portDate;

    public PortTime() throws IOException {
        loadSavedTime();
    }

    private void loadSavedTime() throws IOException {
        File file = new File("savedTime.txt");

        if(!file.exists()){
            file.createNewFile();
            portDate = LocalDate.now();
        }
        else{
            Scanner scanner = new Scanner(file);

            String line = scanner.nextLine();
            portDate = LocalDate.parse(line);
        }
    }

    public void savePortTime() throws IOException {
        File file = new File("savedTime.txt");

        if(file.exists()){
            file.delete();
            file.createNewFile();
        }

        PrintWriter printer = new PrintWriter(new FileWriter(file));
        printer.println(portDate.toString());
        printer.close();
    }

    @Override
    public void run() {
        while(true){
            portDate = portDate.plusDays(1);
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public LocalDate getPortDate() {
        return portDate;
    }

    public void showCurrentDate(){
        System.out.println("");
        System.out.println("Current date: " + getPortDate());
        System.out.println("");
    }
}
