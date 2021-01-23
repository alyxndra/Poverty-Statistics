package a02;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.*;

/**
 * A program that reads data about family incomes and produces a report on
 * poverty rates using the provided data.
 *
 * @author Alexandra Embree (A00443068)
 */
public class PovertyData {

    //Class constants
    public static final Scanner KBD = new Scanner(System.in);
    public static final double RELATIVE_NEEDS_RATIO = 0.6;
    public static final int MINIMUM_FAMILY_NUM = 2;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Introduce program
        printIntroduction();
        pause();
        
        //Collect user inputs
        double[] theIncomes = getTheIncomes();
        double basicNeedsIncome = getTheBasicNeedsIncome();
        pause();
        
        //Print a report 
        printReport(theIncomes, basicNeedsIncome);
        pause();
    }

    /**
     * A method that prints a brief description of the program.
     */
    public static void printIntroduction() {
        System.out.println("Poverty Rate Program");
        System.out.println("--------------------\n");
        System.out.println("This program reads data on family incomes and"
                + " produces a report on poverty rates among the families.\n");
        System.out.println("By Alexandra Embree (A00443068)");
        System.out.println("For CSCI 1228, Winter 2021");
    }

    /**
     * A method that pauses to wait for the user to press enter.
     */
    public static void pause() {
        System.out.print("\n...press enter...");
        KBD.nextLine();
        System.out.println();
    }

    /**
     * A method that asks the user for information about the sample population
     * and records family incomes in an array.
     *
     * @return an array of incomes
     */
    public static double[] getTheIncomes() {
        //Get number of families to determine array length
        int numFamilies;
        System.out.print("How many families are there in your sample? ");
        numFamilies = KBD.nextInt();
        KBD.nextLine();
        
        //Verify number of families is valid
        while (!isValidPopulation(numFamilies)) {
            System.out.println("There must be at least 2 families.");
            System.out.print("How many families are there in your sample? ");
            numFamilies = KBD.nextInt();
            KBD.nextLine();
        }
        
        //Create array
        double[] theIncomes = new double[numFamilies];
        
        //Fill array with user inputs
        for (int i = 0; i < numFamilies; i++) {
            System.out.print("What is the income for family #"
                    + (i + 1) + "? ");
            theIncomes[i] = KBD.nextDouble();
            KBD.nextLine();
            //Verify that incomes are positive numbers
            while (!isValid(theIncomes[i])) {
                System.out.println("The income must be non-negative.");
                System.out.print("What is the income for family #"
                        + (i + 1) + "? ");
                theIncomes[i] = KBD.nextDouble();
                KBD.nextLine();
            }
        }
        return theIncomes;
    }

    /**
     * A method that takes income required to support a family as input.
     *
     * @return the basic needs income of the sample population
     */
    public static double getTheBasicNeedsIncome() {
        
        //Ask user for basic needs income
        double basicIncome;
        System.out.print("What is the minimum income required to support "
                + "a family? ");
        basicIncome = KBD.nextDouble();
        KBD.nextLine();
        
        //Verify that basic income is a positive number
        while (!isValid(basicIncome)) {
            System.out.println("The income must be non-negative.");
            System.out.print("What is the minimum income required to "
                    + "support a family? ");
            basicIncome = KBD.nextDouble();
            KBD.nextLine();
        }
        return basicIncome;
    }

    /**
     * A method that prints a report with statistics about incomes and poverty
     * rates in the sample population.
     *
     * @param incomes the array of incomes of the sample population
     * @param basicNeedsInc the basic needs income for the sample population
     */
    public static void printReport(double[] incomes, double basicNeedsInc) {
        
        //Get data required by using calculating methods
        double average = getAverageIncome(incomes);
        double absolutePovertyRate = getPercentageBelow(incomes, basicNeedsInc);
        double median = getMedianIncome(incomes);
        double relativeNeedsIncome = median * RELATIVE_NEEDS_RATIO;
        double relativePovertyRate = getPercentageBelow(incomes,
                relativeNeedsIncome);
        
        //Print the information
        System.out.println("Report");
        System.out.println("------\n");
        System.out.println("Average Income:        $" + average);
        System.out.println("Basic Needs Income:    $" + basicNeedsInc);
        System.out.println("Absolute Poverty Rate:  " + absolutePovertyRate
                + "%\n");
        System.out.println("Median Income:         $" + median);
        System.out.println("Relative Needs Income: $" + relativeNeedsIncome);
        System.out.println("Relative Poverty Rate:  " + relativePovertyRate
                + "%\n");
    }

    /**
     * A method that calculates the average income from an array.
     *
     * @param incomes the array of incomes of the sample population
     * @return the average income
     */
    public static double getAverageIncome(double[] incomes) {
        double average = DoubleStream.of(incomes).sum() / incomes.length;
        return average;
    }

    /**
     * A method that calculates the median income from an array.
     *
     * @param incomes the array of incomes of the sample population
     * @return the median income
     */
    public static double getMedianIncome(double[] incomes) {
        Arrays.sort(incomes);
        double median;
        if (incomes.length % 2 == 0) {
            median = ((double) incomes[incomes.length / 2]
                    + (double) incomes[incomes.length / 2 - 1]) / 2;
        } else {
            median = (double) incomes[incomes.length / 2];
        }
        return median;
    }

    /**
     * A method that calculates the percentage of incomes below a given value.
     *
     * @param incomes the array of incomes of the sample population
     * @param Inc the dividing income, below which counts towards the return
     * @return the percentage of incomes in the sample population below the
     * given value
     */
    public static double getPercentageBelow(double[] incomes, double Inc) {
        //Loop through incomes, if an income is below given value add to count
        int count = 0;
        for (int i = 0; i < incomes.length; i++) {
            if (incomes[i] < Inc) {
                count++;
            }
        }
        //Divide number of incomes below given value by total number of incomes
        double percentage = (double) count / incomes.length * 100;
        return percentage;
    }

    /**
     * A method that determines whether income input is above 0.
     *
     * @param income the income input to check
     * @return true if positive
     */
    public static boolean isValid(double income) {
        return income >= 0;
    }

    /**
     * A method that determines whether the sample population size is valid.
     *
     * @param numberFamilies user input to check
     * @return true if sample population is above minimum
     */
    public static boolean isValidPopulation(int numberFamilies) {
        return numberFamilies >= MINIMUM_FAMILY_NUM;
    }

}
