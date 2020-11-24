package Tests;

import DataStructures.CSR;
import DataStructures.Table;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Test1 {
    public static void main(String[] args) throws InterruptedException {
        String path = "C://Users//RaffaBerzo//OneDrive - Politecnico di Milano//Codici//Java//Contest//soc-pokec-relationships.txt";



        CSR csr = new CSR();
        Table table = new Table();

        Thread loadCSR = new Thread(() -> {
            try {
                csr.DataStorageAll(path);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        Thread loadTable = new Thread(() -> {
            try {
                table.DataStorageAll(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        long startTimeBoth = System.nanoTime();
        loadCSR.start();
        loadTable.start();
        loadCSR.join();
        loadTable.join();
        long endTimeBoth = System.nanoTime();
        float totalTimeBoth = (float) ((endTimeBoth*1.0 - startTimeBoth*1.0)/1000000000);

        System.out.println("Table and CSR loaded in " + totalTimeBoth + " seconds");

    }
}
