package Pokec;

import org.eclipse.collections.impl.list.mutable.primitive.IntArrayList;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        String p = "C://Users//aucol//Documents//Università//Nects//Oracle//soc-pokec-relationships.txt"; //Please fill in with your file directory
        CSR csr = new CSR();
        IntTable table = new IntTable();
        ArrayList<IntArrayList> results;

        System.out.println("Loading data...");

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
        System.out.println("Table and CSR loaded in " + totalTimeBoth + " seconds");

        Scanner input = new Scanner(System.in);

        int userID;
        int depth;

        while(true){
            System.out.print("UserID: ");
            userID = input.nextInt();
            System.out.print("depth: ");
            depth = input.nextInt();
            long start = System.nanoTime();
            results=Engine(csr, table, userID, depth);
            long end = System.nanoTime();
            float totalTime = (float) ((end*1.0 - start*1.0)/1000000000);
            System.out.println(results.size() + " results given in " + totalTime + " seconds");
        }
    }

    private static ArrayList<IntArrayList> Engine(CSR csr, IntTable table, int userID, int depth) throws InterruptedException {
        int n_friends = csr.numberOfFriends(userID);
        if(Math.round(6.4 -0.274*Math.log(n_friends))<=depth){
            System.out.println("Hash Join operation...");
            return table.hashJoin(userID, depth);
        }else{
            System.out.println("CSR-Traversal operation...");
            return csr.multipleTraverse(userID, depth);
        }
    }
}
