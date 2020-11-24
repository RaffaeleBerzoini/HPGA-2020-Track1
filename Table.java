package Pocek;


import java.io.IOException;
import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;

public class Table {

    private ArrayList<Integer> user;
    private ArrayList<Integer> friend;

    public void DataStorageAll(String path) throws IOException{
        this.user = new ArrayList<>();
        this.friend = new ArrayList<>();
        Scanner scanner = new Scanner(new File(path));
        int i=0;
        while(scanner.hasNextInt()){
            user.add(i,scanner.nextInt());
            friend.add(i,scanner.nextInt());
            i++;
        }
    }
    public void DataStorageSubset(String path, int n_rel) throws IOException{
        this.user = new ArrayList<>();
        this.friend = new ArrayList<>();
        Scanner scanner = new Scanner(new File(path));
        for(int i=0; i<n_rel; i++){
            user.add(i,scanner.nextInt());
            friend.add(i,scanner.nextInt());
        }
    }
}
