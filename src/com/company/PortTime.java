package com.company;

import com.company.warehouse.Warehouse;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Scanner;

public class PortTime extends Thread {

    private LocalDate portDate;
    private final Warehouse warehouse;

    public PortTime(Warehouse warehouse){
        this.warehouse = warehouse;
    }

    public void loadSavedTime() throws IOException {
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
        while(!isInterrupted()){
            try {
                portDate = portDate.plusDays(1);

                synchronized (warehouse){
                    warehouse.notify();
                }

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
