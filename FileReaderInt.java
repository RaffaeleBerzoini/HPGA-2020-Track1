package Tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class FileReaderInt {
    public static void main(String [] args) throws IOException {
        String path = "C://Users//RaffaBerzo//OneDrive - Politecnico di Milano//Codici//Java//Contest//soc-pokec-relationships.txt";
        Integer a;
        Scanner scanner = new Scanner(new File(path));

        while(scanner.hasNextInt()){
            a = new Integer(scanner.nextInt());
            System.out.println(a);
        }
    }

}
