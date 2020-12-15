package Pokec;
import org.eclipse.collections.impl.list.mutable.primitive.IntArrayList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class CSR {
    private IntArrayList ptr;
    private IntArrayList idx;
    private FileWriter myWriter;


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

    private IntArrayList singleTraverse (int userID) {
        IntArrayList friends = new IntArrayList();
        try {
            for (int i = ptr.get(userID - 1); i < ptr.get(userID); i++) {
                friends.add(idx.get(i));
            }
        }catch(Exception e){
            //if the given user is not loaded the path is interrupted
        }
        return friends;
    }

    public ArrayList<IntArrayList> multipleTraverse(int userID, int depth) throws InterruptedException {   //See HashJoin comments
        ArrayList<IntArrayList> links = new ArrayList<IntArrayList>();
        ArrayList<IntArrayList> links2 = new ArrayList<IntArrayList>();
        ArrayList<IntArrayList> links3 = new ArrayList<IntArrayList>();
        ArrayList<IntArrayList> links4 = new ArrayList<IntArrayList>();
        ArrayList<IntArrayList> links5 = new ArrayList<IntArrayList>();
        ArrayList<IntArrayList> links6 = new ArrayList<IntArrayList>();
        ArrayList<IntArrayList> links7 = new ArrayList<IntArrayList>();
        ArrayList<IntArrayList> links8 = new ArrayList<IntArrayList>();

        IntArrayList friends = singleTraverse(userID);
        int start = 0;
        int end = friends.size();
        int partitions = 8;
        int length = (end-start)/partitions;

        try {
            this.myWriter = new FileWriter("FriendsCSR.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Thread t1 = new Thread(()->{
            for (int i = start; i < start + length; i++){
                IntArrayList currentPath = new IntArrayList();
                currentPath.add(userID);
                currentPath.add(friends.get(i));
                followPath(currentPath, depth-1, links);
            }
        });
        Thread t2 = new Thread(()->{
            for (int i = start + length; i < start + 2*length; i++){
                IntArrayList currentPath = new IntArrayList();
                currentPath.add(userID);
                currentPath.add(friends.get(i));
                followPath(currentPath, depth-1, links2);
            }
        });
        Thread t3 = new Thread(()->{
            for (int i = start + 2*length; i < start + 3*length; i++){
                IntArrayList currentPath = new IntArrayList();
                currentPath.add(userID);
                currentPath.add(friends.get(i));
                followPath(currentPath, depth-1, links3);
            }
        });
        Thread t4 = new Thread(()->{
            for (int i = start+3*length; i < start+4*length; i++){
                IntArrayList currentPath = new IntArrayList();
                currentPath.add(userID);
                currentPath.add(friends.get(i));
                followPath(currentPath, depth-1, links4);
            }
        });
        Thread t5 = new Thread(()->{
            for (int i = start+4*length; i < start+5*length; i++){
                IntArrayList currentPath = new IntArrayList();
                currentPath.add(userID);
                currentPath.add(friends.get(i));
                followPath(currentPath, depth-1, links5);
            }
        });
        Thread t6 = new Thread(()->{
            for (int i = start+5*length; i < start+6*length; i++){
                IntArrayList currentPath = new IntArrayList();
                currentPath.add(userID);
                currentPath.add(friends.get(i));
                followPath(currentPath, depth-1, links6);
            }
        });
        Thread t7 = new Thread(()->{
            for (int i = start+6*length; i < start+7*length; i++){
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

        for (int i = start+7*length; i < end; i++){
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

    private void followPath(IntArrayList path, int depth, ArrayList<IntArrayList> links) {     //See IntTable.followPath
        if (depth == 0) {
            if (!duplicate(copy(path))) {
                links.add(path);
                /*
                    try {
                        myWriter.write(String.valueOf(path)+"\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                 */

            }
        }else {
            IntArrayList friends = singleTraverse(path.get(path.size() - 1));
            for (int i = 0; i < friends.size(); i++) {
                IntArrayList newPath = copy(path);
                newPath.add(friends.get(i));
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
        toCheck.sortThis();
        int length = toCheck.size();
        int prev = toCheck.get(0);
        for (int i = 1; i < length; i++) {
            if (toCheck.get(i) == prev)
                return true;
            prev = toCheck.get(i);
        }
        return false;
    }

    public int numberOfFriends(int userID){
        return ptr.get(userID)-ptr.get(userID-1);
    }
}
