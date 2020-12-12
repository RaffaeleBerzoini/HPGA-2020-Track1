package Pokec;

import org.eclipse.collections.impl.list.mutable.primitive.IntArrayList;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class StoringData {

    public static void main(String[] args) throws IOException, InterruptedException {
        String p = "C://Users//aucol//Documents//Università//Nects//Oracle//soc-pokec-relationships.txt";
        CSR csr = new CSR();
        IntTable table = new IntTable();

        Thread loadCSR = new Thread(() -> {
            try {
                csr.DataStorageAll(p);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        Thread loadTable = new Thread(() -> {
            try {
                table.DataStorageAll(p);
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
        System.out.println("Table, HashMap CSR loaded in " + totalTimeBoth + " seconds");

        Scanner input = new Scanner(System.in);

        int userID;
        int depth;

        while(true){
            System.out.print("UserID: ");
            userID = input.nextInt();
            System.out.print("depth: ");
            depth = input.nextInt();
            //System.out.println(csr.singleTraverse(userID));
            //System.out.println(table.depth_1(userID));
            //System.out.println(table.HashJoin(userID, depth));
            System.out.println(table.hashJoin(userID, depth).size());
            System.out.println(csr.multipleTraverse(userID, depth).size());
        }

    }
}