package Pocek;

import org.eclipse.collections.impl.list.mutable.primitive.IntArrayList;

import java.io.IOException;
import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;

public class IntTable {

    private IntArrayList user;
    private IntArrayList friend;

    public void DataStorageAll(String path) throws IOException{
        this.user = new IntArrayList();
        this.friend = new IntArrayList();
        Scanner scanner = new Scanner(new File(path));
        int i=0;
        while(scanner.hasNextInt()){
            user.addAtIndex(i,scanner.nextInt());
            friend.addAtIndex(i,scanner.nextInt());
            i++;
        }
    }
    public void DataStorageSubset(String path, int n_rel) throws IOException{
        this.user = new IntArrayList();
        this.friend = new IntArrayList();
        Scanner scanner = new Scanner(new File(path));
        for(int i=0; i<n_rel; i++){
            user.addAtIndex(i,scanner.nextInt());
            friend.addAtIndex(i,scanner.nextInt());
        }
    }
}
