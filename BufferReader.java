package DataReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BufferReader {
    public static void main(String [] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("C://Users//RaffaBerzo//OneDrive - Politecnico di Milano//Codici//Java//Contest//soc-pokec-relationships.txt"));
        String line = reader.readLine();
        while(line!=null) {
            System.out.println(line);
            line = reader.readLine();
        }
    }
}
