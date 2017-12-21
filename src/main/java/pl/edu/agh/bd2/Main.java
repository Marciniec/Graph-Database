package pl.edu.agh.bd2;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Solution solution = new Solution();
        solution.dropDatabase();
        solution.populateDatabase();
        solution.databaseStatistics();
        System.out.println(solution.getAllRelationshipsOfNode(49));
        System.out.println(solution.pathBetweenNodes(10,1));
    }


}
