package DataStructures;

import org.eclipse.collections.impl.list.mutable.primitive.IntArrayList;

import java.io.IOException;
import java.io.File;
import java.util.Scanner;

public class Table {

    private IntArrayList user;
    private IntArrayList friend;

    public void DataStorageAll(String path) throws IOException{
        this.user = new IntArrayList();
        this.friend = new IntArrayList();
        Scanner scanner = new Scanner(new File(path));
        while(scanner.hasNextInt()){
            user.add(scanner.nextInt());
            friend.add(scanner.nextInt());
        }
    }
    public void DataStorageSubset(String path, int n_rel) throws IOException{
        this.user = new IntArrayList();
        this.friend = new IntArrayList();
        Scanner scanner = new Scanner(new File(path));
        for(int i=0; i<n_rel; i++){
            user.add(scanner.nextInt());
            friend.add(scanner.nextInt());
        }
    }

    public void printRelationships(){
        for(int i=0; i<user.size(); i++){
            System.out.print(user.get(i));
            System.out.print("  ");
            System.out.println(friend.get(i));
        }
    }
}
