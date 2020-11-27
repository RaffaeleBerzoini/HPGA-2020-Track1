package Pocek;

import org.eclipse.collections.impl.list.mutable.primitive.IntArrayList;

import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
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
            user.add(scanner.nextInt());
            friend.add(scanner.nextInt());
            i++;
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
    public IntArrayList HashJoin(int j){
        IntArrayList Pk = new IntArrayList();
        ArrayList<ArrayList<IntArrayList>> map = new ArrayList<>();
        ArrayList<IntArrayList> keys = new ArrayList<>();
        IntArrayList list = new IntArrayList();
        for(int i=0; i<this.user.size(); i++){
            if(i==this.user.size()-1 || this.user.get(i)!=this.user.get(i+1)){
                Pk.add(this.user.get(i));
                if(i!=this.user.size()-1 && this.user.get(i+1)-this.user.get(i)!=1){
                    for(j=0; j<(this.user.get(i+1)-this.user.get(i)); j++)
                        Pk.add(0);
                }
            }
        }
        keys.add(list);
        for(int i=0; i<this.user.size(); i++){
            list.add(i);
            if(i==this.user.size()-1 || this.user.get(i)!=this.user.get(i+1)) {
                System.out.println('-');
                keys.add(this.user.get(i) % 5, list);
                if(i==this.user.size()-1 || (this.user.get(i)+1)%5==0) {
                    map.add(this.user.get(i) / 5, keys);
                    keys = new ArrayList<>();
                }
                list=new IntArrayList();
            }
        }

        return depth_1(map,j);
    }
    private IntArrayList depth_1(ArrayList<ArrayList<IntArrayList>> map, int j){
        IntArrayList result = new IntArrayList();
        for(int i=0; i<map.get(j/5).get(j%5).size(); i++){
            result.add(this.friend.get(map.get(j/5).get(j%5).get(i)));
        }
        return result;
    }
}
