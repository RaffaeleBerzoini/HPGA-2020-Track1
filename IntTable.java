package Pokec;

import org.eclipse.collections.impl.list.mutable.primitive.IntArrayList;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class IntTable {

    private IntArrayList user;
    private IntArrayList friend;
    ArrayList<ArrayList<IntArrayList>> map;
    private FileWriter myWriter;


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

    private void HashMap(){
        map = new ArrayList<>();
        ArrayList<IntArrayList> keys = new ArrayList<>();
        IntArrayList friendsList = new IntArrayList();
        int current_user;
        int current_key;
        int current_friendsList;
        int previous_key = 0;               //previous_user=0; -> previous_key=previous_user/5; previous_friendsList=previous_user%5;
        int previous_friendsList = 0;
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
                for(int k = 0; k < current_user%5; k ++) {
                    keys.add(new IntArrayList());
                }
                friendsList = new IntArrayList();
            }
            friendsList.add(friend.get(i)); //Adding current user's friend to his friends list
            previous_key = current_key;
            previous_friendsList = current_friendsList;

            if(i==user.size()-1){
                keys.add(friendsList);
                map.add(keys);
            }
        }
    }

    private IntArrayList depth_1(int j) {
        try {
            return map.get(j / 5).get(j % 5);
        }catch(IndexOutOfBoundsException e){
            return new IntArrayList();
        }
    }

    public ArrayList<IntArrayList> hashJoin(int userID, int depth) throws InterruptedException {
        HashMap();
        ArrayList<IntArrayList> links = new ArrayList<IntArrayList>();
        ArrayList<IntArrayList> links2 = new ArrayList<IntArrayList>();
        ArrayList<IntArrayList> links3 = new ArrayList<IntArrayList>();
        ArrayList<IntArrayList> links4 = new ArrayList<IntArrayList>();
        ArrayList<IntArrayList> links5 = new ArrayList<IntArrayList>();
        ArrayList<IntArrayList> links6 = new ArrayList<IntArrayList>();
        ArrayList<IntArrayList> links7 = new ArrayList<IntArrayList>();
        ArrayList<IntArrayList> links8 = new ArrayList<IntArrayList>();
        IntArrayList friends = depth_1(userID);
        int start = 0;
        int end = friends.size();
        int partitions = 8;
        int length = (end-start)/partitions;

        try {
            this.myWriter = new FileWriter("FriendsHJ.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Thread t1 = new Thread(() -> {
            for (int i = start; i < start+length; i++){
                IntArrayList currentPath = new IntArrayList();
                currentPath.add(userID);
                currentPath.add(friends.get(i));
                followPath(currentPath, depth-1, links);
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = start+length; i < start+length*2; i++){
                IntArrayList currentPath = new IntArrayList();
                currentPath.add(userID);
                currentPath.add(friends.get(i));
                followPath(currentPath, depth-1, links2);
            }
        });
        Thread t3 = new Thread(() -> {
            for (int i = start+length*2; i < start+length*3; i++){
                IntArrayList currentPath = new IntArrayList();
                currentPath.add(userID);
                currentPath.add(friends.get(i));
                followPath(currentPath, depth-1, links3);
            }
        });
        Thread t4 = new Thread(() -> {
            for (int i = start+length*3; i < start+length*4; i++){
                IntArrayList currentPath = new IntArrayList();
                currentPath.add(userID);
                currentPath.add(friends.get(i));
                followPath(currentPath, depth-1, links4);
            }
        });
        Thread t5 = new Thread(() -> {
            for (int i = start+length*4; i < start+length*5; i++){
                IntArrayList currentPath = new IntArrayList();
                currentPath.add(userID);
                currentPath.add(friends.get(i));
                followPath(currentPath, depth-1, links5);
            }
        });
        Thread t6 = new Thread(() -> {
            for (int i = start+length*5; i < start+length*6; i++){
                IntArrayList currentPath = new IntArrayList();
                currentPath.add(userID);
                currentPath.add(friends.get(i));
                followPath(currentPath, depth-1, links6);
            }
        });
        Thread t7 = new Thread(() -> {
            for (int i = start+length*6; i < start+length*7; i++){
                IntArrayList currentPath = new IntArrayList();
                currentPath.add(userID);
                currentPath.add(friends.get(i));
                followPath(currentPath, depth-1, links7);
            }
        });

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        t7.start();

        for (int i = start+length*7; i < end; i++){
            IntArrayList currentPath = new IntArrayList();
            currentPath.add(userID);
            currentPath.add(friends.get(i));
            followPath(currentPath, depth-1, links8);
        }

        t1.join();
        t2.join();
        t3.join();
        t4.join();
        t5.join();
        t6.join();
        t7.join();

        links.addAll(links2);
        links2.clear();
        links.addAll(links3);
        links3.clear();
        links.addAll(links4);
        links4.clear();
        links.addAll(links5);
        links5.clear();
        links.addAll(links6);
        links6.clear();
        links.addAll(links7);
        links7.clear();
        links.addAll(links8);
        links8.clear();

        try {
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return links;
    }

    private void followPath(IntArrayList path, int depth, ArrayList<IntArrayList> links){
        if (depth==0){                                                 //When the required depth is reached, it checks if it doesn't contain duplicates and adds the path to "links"
            if(!duplicate(copy(path))){
                links.add(path);
                /*
                    try {
                        myWriter.write(String.valueOf(path)+"\n");  //In order to avoid java memory problems, we save the results on a txt file
                    } catch (IOException e) {                          //FileWriter.write() is Thread safe
                        e.printStackTrace();
                    }

                 */

            }
        }else{
            IntArrayList friends = depth_1(path.get(path.size()-1));  //Storing friends of the last user of the current "path"
            for (int i = 0; i < friends.size(); i++) {
                IntArrayList newPath = copy(path);
                newPath.add(friends.get(i));                          //Storing "friends" one by one into "newPath", which is a copy of the previous path
                followPath(newPath, depth - 1, links);
            }
        }
    }

    private IntArrayList copy(IntArrayList toCopy){
        IntArrayList copied = new IntArrayList();
        for(int i = 0; i < toCopy.size(); i++){
            copied.add(toCopy.get(i));
        }
        return copied;
    }

    private boolean duplicate(IntArrayList toCheck) {
        toCheck.sortThis();                                    //Sorting the array before looking for duplicates make the function faster
        int length = toCheck.size();
        int prev = toCheck.get(0);
        for (int i = 1; i < length; i++) {
            if (toCheck.get(i) == prev)
                return true;                            //True = the path contains duplicates
            prev = toCheck.get(i);
        }
        return false;
    }
}