package DataStructures;

import org.eclipse.collections.impl.list.mutable.primitive.IntArrayList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CSR {
    private IntArrayList ptr;
    private IntArrayList idx;

    public void DataStorageAll(String path) throws FileNotFoundException {
        ptr = new IntArrayList();
        idx = new IntArrayList();
        Scanner scanner = new Scanner(new File(path));
        int current_user = 0;
        int userID = 0;

        while(scanner.hasNextInt()) {
            userID = scanner.nextInt();
            while (userID > current_user + 1) {
                ptr.add(idx.size());
                current_user++;
            }
            if (userID == current_user+1){
                ptr.add(idx.size());
                current_user = userID;
            }
            idx.add(scanner.nextInt());
        }
        ptr.add(idx.size()); //Last val of ptr array
    }

    public void DataStorageSubset(String path, int n_rel) throws FileNotFoundException {
        ptr = new IntArrayList();
        idx = new IntArrayList();
        Scanner scanner = new Scanner(new File(path));
        int current_user = 0;
        int userID = 0;
        int i = 0;

        while(scanner.hasNextInt() && i < n_rel){
            userID = scanner.nextInt();
            while(userID > current_user + 1){
                ptr.add(idx.size());
                current_user++;
            }
            if (userID == current_user+1){
                ptr.add(idx.size());
                current_user = userID;
            }
            idx.add(scanner.nextInt());
            i++;
        }
        ptr.add(idx.size()); //Last val of ptr array
    }

    public void printRelationships(){
        for(int i = 0; i<ptr.size()-1; i++){
            for(int j = ptr.get(i); j < ptr.get(i+1); j++){
                System.out.print(i+1);
                System.out.print("  ");
                System.out.println(idx.get(j));
            }
        }
    }
}
