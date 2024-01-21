package agh.ics.oop.util;

import agh.ics.oop.interfaces.ChangeListener;
import agh.ics.oop.interfaces.WorldMap;
import agh.ics.oop.model.Simulation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CSVFileWriter implements ChangeListener <Simulation> {
    private final FileWriter csvFile;

    public CSVFileWriter(String fileName) throws IOException{
        File file = new File("logs");
        if (!file.isDirectory()) {
            if (file.mkdir()) {
                System.out.println("Logs directory created");
            }
        }
        this.csvFile = new FileWriter("logs/"+fileName);
        this.csvFile.write("day, energy, children, genes, position, descendants, age, grass_eaten, death_day\n");
    }

    @Override
    public void objectChanged(Simulation worldMap, String message) {
        try {
            this.csvFile.write(message);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void closeFile() throws IOException {
        this.csvFile.close();
    }
}
