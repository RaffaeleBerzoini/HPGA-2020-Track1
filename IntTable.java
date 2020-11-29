package Pocek;

import org.eclipse.collections.impl.list.mutable.primitive.IntArrayList;

import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class IntTable {

    private IntArrayList user;
    private IntArrayList friend;
    ArrayList<ArrayList<IntArrayList>> map;
    ArrayList<IntArrayList> paths = new ArrayList<>();
    IntArrayList line = new IntArrayList();

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

    public void HashMap(){
        map = new ArrayList<>();
        ArrayList<IntArrayList> keys = new ArrayList<>();
        IntArrayList friendsList = new IntArrayList();
        int current_user;
        int current_key;
        int current_friendsList;
        int previous_user = 0;
        int previous_key = previous_user/5;
        int previous_friendsList = previous_user%5;
        for(int i = 0; i<user.size(); i++){
            current_user = user.get(i);
            current_key = current_user/5; //Map row
            current_friendsList = current_user%5; //Keys column(i.e. where to store user's friend list)
            if(current_friendsList != previous_friendsList && current_key == previous_key){
                keys.add(friendsList); //storing the previous user's friend list in Keys and generating a new friend list for the current one
                for(int j = 0; j < current_friendsList-previous_friendsList-1; j++) {
                    friendsList = new IntArrayList(); //storing empty lists for those user who doesn't exist
                    keys.add(friendsList); //adding them in the current key
                }
                friendsList = new IntArrayList();
            }
            if(current_key != previous_key){
                keys.add(friendsList);
                map.add(keys);
                for(int j = 0; j < current_key-previous_key-1; j++){
                    keys = new ArrayList();
                    map.add(keys);
                }
                keys = new ArrayList();
                //last change
                for(int k = 0; k < current_user%5; k ++){
                    keys.add(new IntArrayList());
                }
                //end of last change
                friendsList = new IntArrayList();
            }
            friendsList.add(friend.get(i)); //Adding current user's friend to his friends list
            previous_user = current_user;
            previous_key = current_key;
            previous_friendsList = current_friendsList;

            if(i==user.size()-1){
                keys.add(friendsList);
                map.add(keys);
            }
        }
    }

    public IntArrayList depth_1(int j) {
        try {
            System.out.println(map.get(j/5).get(j%5));
            return map.get(j / 5).get(j % 5);
        }catch(IndexOutOfBoundsException e){
            return new IntArrayList();
        }
    }


    public ArrayList<IntArrayList> HashJoin(int n_user, int depth){
        IntArrayList line_copy;
        if(depth==1){
            for(int i = 0; i< map.get(n_user/5).get(n_user%5).size(); i++){
                line.add(map.get(n_user / 5).get(n_user % 5).get(i));
                line_copy = copy(line);
                paths.add(line_copy);
                line.removeAtIndex(line_copy.size()-1);
            }
        }else{
            for(int i = 0; i< map.get(n_user/5).get(n_user%5).size(); i++){
                line.add(map.get(n_user/5).get(n_user%5).get(i));
                paths=HashJoin(map.get(n_user/5).get(n_user%5).get(i), depth-1);
                line.removeAtIndex(line.size()-1);
            }
        }
        System.out.println(paths);
        System.out.println(paths.size());
        return paths;
    }
    public IntArrayList copy(IntArrayList a) {
        IntArrayList b = new IntArrayList();
        for (int i = 0; i < a.size(); i++)
            b.add(a.get(i));
        return b;
    }
}