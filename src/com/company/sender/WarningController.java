package com.company.sender;

import com.company.Main;
import com.company.warehouse.StoredContainer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Scanner;

public class WarningController {

    public void loadWarnings() throws IOException {
        File file = new File("warnings.txt");

        if(!file.exists()){
            file.createNewFile();
        }

        Scanner scanner = new Scanner(file);

        while(scanner.hasNext()){
            String line = scanner.nextLine();
            if(line.contains("/")){
                String userID = scanner.nextLine().split(" ")[1];

                String text = Main.getContainerController().getTextBetweenSymbols(scanner, "/");

                Warning warning = stringToWarning(text.split(";"));
                Main.getSenderController().getSender(userID).addWarning(warning);
            }
        }
    }

    public void saveWarnings() throws IOException {
        File file = new File("warnings.txt");

        if(file.exists()){
            file.delete();
            file.createNewFile();
        }

        PrintWriter printWriter = new PrintWriter(new FileWriter(file));

        for(Sender sender : Main.getSenderController().getSenders()){
            for(Warning warning : sender.getWarnings()){
                printWriter.println("/");
                printWriter.println("userID: " + sender.getUserID());
                printWriter.println(warning.toString());
                printWriter.println("/");
            }
        }

        printWriter.close();
    }

    private Warning stringToWarning(String[] lines){
        String[] storedContainerLines = new String[lines.length - 1];
        for(int i = 0; i < lines.length - 1; i++){
            storedContainerLines[i] = lines[i];
        }

        StoredContainer storedContainer = Main.getContainerController().stringToStoredContainer(storedContainerLines);
        LocalDate date = LocalDate.parse(lines[lines.length - 1].split(" ")[1]);
        return new Warning(date, storedContainer);
    }
}
