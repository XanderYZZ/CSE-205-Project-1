/************************************************************************************************
 * CLASS: CSE 205: Object-Oriented Programming & Data Structures (Project 1 assignment - Main.java)
 *
 * DESCRIPTION
 * This file contains the code for the Project 1 assignment.
 *
 * COURSE AND PROJECT INFORMATION
 * CSE205 Object Oriented Programming and Data Structures, Spring 2022
 * Project Number: Project #1
 *
 * AUTHOR: Xander Arnspiger, xarnspig, xarnspig@asu.edu, ASU ID: 1220789908 **
 ************************************************************************************************/

// Packages
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

//Main.java
public class Main {
    final static int RUNS_UP = 1;
    final static int RUNS_DN = -1;

    /**
     * The entry point
    */
    public static void main(String[] pArgs) {
        Main mainObject = new Main();
        mainObject.run();
    }

    /**
     * The starting code is here. This is called right when the program starts. This method will create an array list of integers named list, then read the input file, then it will calculate the runs and put it in an output file.
    */
    private void run() {
        ArrayList<Integer> list;
        try {
            list = readInputFile("p1-in.txt");

            ArrayList<Integer> listRunsUpCount = new ArrayList<Integer>();
            ArrayList<Integer> listRunsDnCount = new ArrayList<Integer>();

            listRunsUpCount = findRuns(list, RUNS_UP);
            listRunsDnCount = findRuns(list, RUNS_DN);

            ArrayList<Integer> listRunsCount = mergeLists(listRunsUpCount, listRunsDnCount);

            try {
                writeOutputFile("p1-runs.txt", listRunsCount);
            } catch (FileNotFoundException e) {
                System.out.println("Could not find file!"); // Was unaware what message to put here? 
                System.exit(-200);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Could not find file!"); // Was unaware what message to put here? 
            System.exit(-100);
        }
    }

    /**
     * This method will calculate the runs in the array list. This takes two arguments, the array list named pList and the pDir (runs which are up or down). It calculates both run ups and run downs and then returns the new list.
    */
    private ArrayList<Integer> findRuns(ArrayList<Integer> pList, int pDir) {
        ArrayList<Integer> listRunsCount = arrayListCreate(pList.size(), 0);

        int i = 0;
        int k = 0;

        while (i < pList.size() - 1) {
            if (pDir == RUNS_UP && pList.get(i) <= pList.get(i + 1)) {
                k++;
            } else if (pDir == RUNS_DN && pList.get(i) >= pList.get(i + 1)) {
                k++;
            } else {
                if (k != 0) {
                    listRunsCount.set(k, listRunsCount.get(k) + 1);
                    k = 0;
                }
            }

            i++;
        }

        if (k != 0) {
            listRunsCount.set(k, listRunsCount.get(k) + 1);
        }

        return listRunsCount;
    }

    /**
     * This method creates an array list, taking two arguments. The first argument is the size of the array list, the next argument is the initial value to set all the indexes at.
    */
    private ArrayList<Integer> arrayListCreate(int pSize, int initValue) {
        ArrayList<Integer> list = new ArrayList<Integer>();

        for (int i = 0; i < pSize; i++) {
            list.add(initValue);
        }

        return list;
    }

    /**
     * This method merges two lists together, takes two arguments which are both array lists. This method creates a new array list, sets the indexes by combining both of the arrays, then returns this new array list.
    */
    private ArrayList<Integer> mergeLists(ArrayList<Integer> pListRunsUpCount, ArrayList<Integer> pListRunsDnCount) {
        ArrayList<Integer> listRunsCount = arrayListCreate(pListRunsUpCount.size(), 0);

        for (int i = 0; i <= pListRunsUpCount.size() - 1; i++) {
            listRunsCount.set(i, pListRunsUpCount.get(i) + pListRunsDnCount.get(i));
        }

        return listRunsCount;
    }

    /**
     * This method writes an output file. It takes two arguments, the first is the new file name to name the output file, the next is the array list of runs. We create/open this file, write the runs, then close it.
    */
    private void writeOutputFile(String pFilename, ArrayList<Integer> pListRuns) throws FileNotFoundException {
        File out = new File(pFilename);
        PrintWriter writer = new PrintWriter(out);

        int sum = 0;

        for (int i = 1; i <= pListRuns.size() - 1; i++) {
            sum += pListRuns.get(i);
        }

        writer.println("runs_total: " + sum);

        for (int k = 1; k <= pListRuns.size() - 1; k++) {
            writer.println("runs_" + k + ": " + pListRuns.get(k));
        }

        writer.close();
    }

    /**
     * This method reads the input file. We take one argument, the name of the file. We then check for all ints, put the ints in a list, then return the list.
    */
    private ArrayList<Integer> readInputFile(String pFilename) throws FileNotFoundException {
        ArrayList<Integer> list = new ArrayList<Integer>();
        File in = new File(pFilename);

        Scanner sc = new Scanner(in);

        while (sc.hasNextInt()) {
            int i = sc.nextInt();
            list.add(i);
        }

        sc.close();

        return list;
    }
}