package FinalExam;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by Oxana on 05/06/2018
 */


public class Wilma {
    static Scanner scanner;
    static FileReader reader;
    static PrintWriter writer;
    final static String PATH = "C:\\JavaStudy\\FileTesters\\";
    final static String COMPANYNAME = PATH + "comnames-1.dat";
    final static String TRANSACTIONS = PATH +"transactions.dat";
    final static String STARTYEAR = PATH + "start.dat";
    final static String DESTINATION = "report.txt";
    final static int COMPANYNUMBER = 6;
    final static int ROWTRANSACTION = 4;
    final static int ROWPRICES = 2;

    //method for reading files
    private static Scanner openInputFile(String sourse){

        try{
            reader = new FileReader(sourse);
            scanner = new Scanner(reader);
        }
        catch (FileNotFoundException error){

            System.out.println("Catch 1: File not found");
            System.out.println("This is:" + sourse);
            System.out.println(error.getMessage());
        }
        return scanner;
    }

    //method for open output file
    private static void openOutputFile(){

        //boolean open = true;

        try {
                writer = new PrintWriter(DESTINATION);
        }
        catch (FileNotFoundException error){

                System.out.println("Catch2: Can't open file for output");
                System.out.println(error.getMessage());
                //open = false;
        }
    }//openOutputFile

    //method for close output file
    private static void closeOutputFile(){

        //boolean success = true;

        try {
            writer.println();
            writer.close();
            //success = true;
        }
        catch (Exception error){
            System.out.println("Catch3: error for closing output file");
            System.out.println(error.getMessage());
            //success = false;
        }
    }

    //main method
    public static void main(String[] args)throws IOException{

        String[] company = new String[COMPANYNUMBER];
        int[][] transaction = new int[ROWTRANSACTION][COMPANYNUMBER];
        double[][] price = {{15.00, 10.00, 1.50, 2.00, 11.00, 0.20},
                             {14.50, 14.40, 1.50, 2.10, 9.75, 0.10}};
        int[] start = new int[COMPANYNUMBER];
        int[] finish = new int[COMPANYNUMBER];

        Scanner readTransaction;
        Scanner readStartYear;
        Scanner readCompanies;

        readCompanies = openInputFile(COMPANYNAME );
        readTransaction = openInputFile(TRANSACTIONS);
        readStartYear = openInputFile(STARTYEAR);

        openOutputFile();

        try{
            // read company names from file to array
            for (int i = 0; i < COMPANYNUMBER; i++) {

                if (readCompanies.hasNext()) {

                    company[i] = readCompanies.nextLine();
                    System.out.print(company[i] + "\t\t");
                }
            }
            System.out.println();


            //read transactions from file to array
            for (int row = 0; row <= ROWTRANSACTION; row++){

                    for (int column = 0; column < COMPANYNUMBER; column++){

                        if(readTransaction.hasNextInt()) {

                            transaction[row][column] = readTransaction.nextInt();
                            System.out.print("\t" +transaction[row][column] + "\t\t");
                        }
                    }//for
                System.out.println();
            }//for
            System.out.println();

            //make an array from shares at the start of the year
            System.out.println("\t\t\tShares at the start of the year");

            for (int i = 0; i < COMPANYNUMBER; i++){
                if (readStartYear.hasNextInt()){

                    start[i] = readStartYear.nextInt();
                    System.out.print("\t" + start[i] + "\t\t");
                }
            }
        }//try
        catch (Exception error){

            System.out.println("Catch4: Error");
            System.out.println(error.getMessage() + "caught");
        }

        //count total number of shares at start of the year
        int totalStart = 0;
        int totalColumn;
        int[] totalSharesThisYearColumns = new int[COMPANYNUMBER];
        int totalSharesForYear = 0;
        int totalSharesEndYear;

        try {
            for (int i = 0; i < COMPANYNUMBER; i++){

                totalStart += start[i];
                }
            System.out.println( "\n\nTotal number of shares (start of the year): " + totalStart);
            writer.println( "\n\nTotal number of shares (start of the year): " + totalStart + "\n");

            //count shares for the year
            System.out.println("\n\t\t\tShares for the whole year");
            for (int column = 0; column < COMPANYNUMBER; column++) {
                totalColumn = 0;
                for (int row = 0; row < ROWTRANSACTION; row++) {

                    totalColumn = totalColumn + transaction[row][column];
                    totalSharesForYear += transaction[row][column];//count total shares for year, all rows and columns
                }
                totalSharesThisYearColumns[column] += totalColumn;//printing row of total shares in each column

                System.out.print("\t" + totalSharesThisYearColumns[column] + "\t\t");
            }

            System.out.println("\n\nTotal number of shares (for the year): " + totalSharesForYear);

            //count total shares at the end of year
            System.out.println("\n\t\t\tShares at the end of the year");

            for (int i = 0; i < COMPANYNUMBER; i++) {

                finish[i] = start[i] + totalSharesThisYearColumns[i];//filling array with final shares

                System.out.print("\t" +finish[i] + "\t\t");
            }
            totalSharesEndYear = totalSharesForYear + totalStart;

            System.out.println("\n\nTotal number of shares (end of year): " + totalSharesEndYear);
            writer.println("Total number of shares (end of year):       " + totalSharesEndYear);

        }
        catch (Exception error){
            System.out.println("Catch5: Error");
            System.out.println(error.getMessage() + "caught");
        }
        System.out.println();

        //make an array for prices
        System.out.println("\t\t\tShare prices for the beginning and end of the year\n");
        try {

            for(int row = 0; row < ROWPRICES; row++){
                for (int column = 0; column < COMPANYNUMBER; column++){
                    System.out.print("\t" + price[row][column] + "\t\t");
                }
                System.out.println();
            }

            //count initial value of Wilma's shares

            double totalValueStart = 0;
            System.out.println("\n\t\t\tTotal value shares at the beginning of the year\n");
            for (int i = 0; i < COMPANYNUMBER; i++){

                if(readStartYear.hasNextInt()){
                    start[i] = readStartYear.nextInt();
                }

                System.out.print("\t" +start[i] * price[0][i] + "\t\t");
                totalValueStart+= start[i] * price[0][i];
            }//for
            System.out.println("\n\nInitial value of shares is: £" + totalValueStart);
            writer.println("\n\nInitial value of shares is:  £" + totalValueStart);

            //count final value of shares at the end of year

            double totalValueFinish = 0;
            System.out.println("\n\t\t\tTotal value shares at the end of the year\n");

            for (int i = 0; i < COMPANYNUMBER; i++){

                System.out.print("\t" + finish[i] * price[1][i] + "\t\t");
                totalValueFinish+= finish[i] * price[1][i];
            }
            System.out.println("\n\nFinal value of shares is: £" + totalValueFinish);
            writer.println("\n\nFinal value of shares is:    £" + totalValueFinish);

            readStartYear.close();
            readCompanies.close();
            readTransaction.close();
        }//try
        catch (Exception error){
            System.out.println("Catch5: Error");
            System.out.println(error.getMessage() + "caught");
        }

        //make an array for prices

    writer.close();
    }//main
}
