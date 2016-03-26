/**
 *
 * @author Gizzle Bautista ( S0261553 )
 * COIT20245 - Introduction to Programming
 * Assignment 2 - Sales and Commissions Data Manager
 */

package coit20245ass2;


import java.util.Scanner;


public class SalesDataManager {

    //variables for headers and formatting
    private String centerHeader1   = "**********Sales & Commissions Data Manager**********";
    private String centerHeader2   = "**********Sales and Commissions**********";
    private String centerHeader3   = "**********A simple statistics**********";
    private String centerFormat     = "\n\n\n\t\t\t\t\t";
    private String divider          = "----------------------------------------------------------------------------------------------------------------";
    private String divider2          = "================================================================================================================";
    private String option1          = "1. Input & validate data";
    private String option2          = "2. Display";
    private String option3          = "3. Sort by name";
    private String option4          = "4. Sort by sales";
    private String option5          = "5. Search by name";
    private String option6          = "6. Search by sales";
    private String option7          = "7. Display statistics";
    private String option8          = "8. Exit";
    private String header1          = "Salesperson";
    private String header2          = "Sales Amount";
    private String header3          = "Commissions";
    
    private static final int NUMBER_OF_SALESPERSON = 10;                     //number of iteration that the program will ask for customer's information
    private String[] spersonName = new String [NUMBER_OF_SALESPERSON];      //array for sales staff
    private int[] salesAmt = new int [NUMBER_OF_SALESPERSON];               //array for sales amount of each sales staff
    private double[] salesCommission = new double [NUMBER_OF_SALESPERSON];  //array for sales commission of each sales staff
    private int menuOption;                                                 //variable for switch case options
    Scanner readText = new Scanner (System.in);                             //object Scanner for reading user input
    
    /**
     * constructor for SalesDataManager
     */
    public SalesDataManager ()
    {
        String[] spersonName;
        int[] salesAmt;
    }
    
    //method to display switch options with methods per option
    public void displayMenu ()
    {
        
        switch (menuOption)
        {
            case 1: //Input & validate data
                    inputData(spersonName,salesAmt,salesCommission);
                    break;
                    
            case 2: //Display - print salesperson + sales amount + commissions array here
                    displayMenu2();
                    printArray(spersonName,salesAmt,salesCommission);
                    System.out.printf("\n\n%10s", "Total: " + NUMBER_OF_SALESPERSON);
                    System.out.println(" data entries");
                    break;
            
            case 3: //Sort by Name - ascending order of fName with Sales and Commissions
                    displayMenu2();
                    printSortedNameArray(spersonName,salesAmt,salesCommission);
                    System.out.println("\n\nTotal: " + NUMBER_OF_SALESPERSON + " data entries");
                    break;
            
            case 4: //Sort by Sales - ascending order of sales
                    displayMenu2();
                    printSortedSalesArray(spersonName,salesAmt,salesCommission);
                    System.out.println("\n\nTotal: " + NUMBER_OF_SALESPERSON + " data entries");
                    break;
                
            case 5: //Search by Name - error validation if name does not match
                    System.out.println("Please enter a sales person name: ");
                    String searchName = readText.nextLine();
                    linearSearch(spersonName,searchName,salesAmt,salesCommission);
                    break;
            
            case 6: //Search by Sales - input validation for range
                    System.out.println("Please enter a specific sales amount: ");
                    int searchSales = readText.nextInt();
                    readText.nextLine();
                    linearSearchSales(salesAmt,searchSales,spersonName);
                    break;
            
            case 7: //Display statistics - lowest & highest value of sales amt + median
                    displayMenu3();
                    displayStatistics(salesAmt,spersonName);
                    break;
                
            case 8: System.exit(0);
                    break;
                
        }        
        
    }
    
    //method for displaying "Sales and Commissions" header with column headers "Salesperson" "Sales Amount" and "Commissions"
    public void displayMenu2 ()
    {
        System.out.println(centerFormat + centerHeader2 + "\n" + divider2);
        System.out.printf("%30s %30s %30s", header1, header2, header3);
        System.out.println("\n" + divider);
    }
    
    //method to display "A simple statistics" header
    public void displayMenu3 ()
    {
        System.out.println(centerFormat + centerHeader3 + "\n" + divider2);
        
    }
    
    //method for inputting sales person's name and sales amount. generates sales commission as well based on inputted sales amount
    public void inputData(String[] sName, int[] salesAmt, double[] sComm)
    {
	int index;
        
        
	Scanner readInput = new Scanner (System.in);
        
        for (index=0; index < spersonName.length; index++)
        {
            System.out.println("\nPlease enter salesperson's name and sales amount.");
            System.out.printf("Enter salesperson name %2d",  index + 1);
            System.out.printf(" : ");
            String name = readInput.nextLine();

            while (!validateName(name) || !validateSpace(name))
            {
                System.out.println("Invalid name input. Name field only accepts English characters and a space between your first and last name.");
                System.out.println("\nPlease enter salesperson's name and sales amount.");
                System.out.printf("Enter salesperson name %2d",  index + 1);
                System.out.printf(" : ");
                name = readInput.nextLine();
            }
            
            if (validateName(name) && validateSpace (name))
                sName[index] = name;
            
            
            System.out.print("Please enter sales amount: ");
            int sAmt = readInput.nextInt();
            readInput.nextLine();
            while ((sAmt < 10) || (sAmt > 100))
            {
                System.out.println("Invalid amount input. Sale input range should be between 10 and 100.");
                System.out.print("Please enter sales amount: ");
                sAmt = readInput.nextInt();
                readInput.nextLine();
            }
            
            if ((sAmt >= 10) || (sAmt <= 100))
            {
            int amtToArray = 1000 * sAmt;
            double totalComm = calculateCommission(amtToArray);
            salesAmt[index] = amtToArray;
            sComm[index] = totalComm;
            }
            
           
        }
    
    }
    
    //method for validating sales person's name
    public boolean validateName (String name)
    {
        return name.matches ("[A-Z][a-zA-Z]*[a-zA-z]+([ '-][a-zA-Z]+)*");
        
    }
    
    //method for validating space between first and last name
    public boolean validateSpace (String name)
    {
        return name.matches (".*\\s+.*");
        
    }
   
    
    //method for calculating commissions
    public double calculateCommission (int salesIncurred)
    {
        /*
          Method for calculating the salesperson's commission
          Sales Amount              Commission Rate
          $10,000 - $25,000                5%
          $25,001 - $50,000                6%
          $50,001 - $75,000                8%
          $75,001 - $100,000               10%
        */    
            double commission =0.00;
            
            if ((salesIncurred >=10000 && salesIncurred <=25000))
            {
                commission = salesIncurred * .05;
            }
            else
                if ((salesIncurred > 25000) && (salesIncurred <=50000))
                {
                    commission = salesIncurred * .06;
                }
            else
                if ((salesIncurred > 50000) && (salesIncurred <=75000))
                {
                    commission = salesIncurred * .08;
                }
            else
                if ((salesIncurred > 75000) && (salesIncurred <=100000))
                {
                    commission = salesIncurred * .1;
                }
                      
            return commission;
    }
    
    //method for printing data for option 2 - salespersons name, sales amount, and commissions
    public void printArray (String[] sName, int[] salesAmt, double[] sComm)
    {
	int index;

	for (index=0; index<spersonName.length; index++)
            System.out.printf ("\n%30s %30s %30s", sName[index], "$" + salesAmt[index], "$" + sComm[index]);
    }
    
    //method for sorting salespersons name with sales amount and commissions
    public void sortByName(String[] listFirstName, int[] salesAmt, double[] sComm)
    {
        int i = 0;
        int j = 0;
        String temp;
        
        for (i=listFirstName.length-1; i>0; i--)
    	{   
            for (j=0; j<i; j++)
            {
            	//if (spersonName[j] > spersonName[j+1])
            	if (listFirstName[j].compareTo(listFirstName[j+1]) > 0)
            	{
                    temp=listFirstName[j];
                    listFirstName[j]=listFirstName[j+1];
                    listFirstName[j+1]=temp;
                    
                    int temp2=salesAmt[j];
                    salesAmt[j]=salesAmt[j+1];
                    salesAmt[j+1]=temp2;
                    
                    double temp3=sComm[j];
                    sComm[j]=sComm[j+1];
                    sComm[j+1]=temp3;
                    
            	}
            }
	}
    }
    
    //method for printing data for option 3 - sorted salespersons name with sales amount and commissions
    public void printSortedNameArray (String[] sName, int[] salesAmt, double[] sComm)
    {
	int index;
        sortByName(sName,salesAmt,sComm);
        
	for (index=0; index<spersonName.length; index++)
            System.out.printf ("\n%30s %30s %30s", sName[index], "$" + salesAmt[index], "$" + sComm[index]);
    }
    
    //method for sorting sales with salespersons name and commissions
    public void sortBySales(String[] listFirstName, int[] salesAmt, double[] sComm)
    {
        int i = 0;
        int j = 0;
        
        
        for (i=listFirstName.length-1; i>0; i--)
    	{   
            for (j=0; j<i; j++)
            {
            	if (salesAmt[j] > salesAmt[j+1])
            	{
                    int temp=salesAmt[j];
                    salesAmt[j]=salesAmt[j+1];
                    salesAmt[j+1]=temp;
                    
                    String temp2 = listFirstName[j];
                    listFirstName[j]=listFirstName[j+1];
                    listFirstName[j+1]=temp2;
                    
                    double temp3=sComm[j];
                    sComm[j]=sComm[j+1];
                    sComm[j+1]=temp3;
                    
            	}
            }
	}
    }
    
    //method for printing data for option 4 - sorted sales amount with salespersons name and commissions
    public void printSortedSalesArray (String[] sName, int[] salesAmt, double[] sComm)
    {
	int index;
        sortBySales(sName,salesAmt,sComm);
        
	for (index=0; index<spersonName.length; index++)
            System.out.printf ("\n%30s %30s %30s", sName[index], "$" + salesAmt[index], "$" + sComm[index]);
    }
    
    //method for searching salespersons name
    public void linearSearch (String[] sName, String salesName, int[] salesAmt, double[] sComm)
    {

        int i = 0;
        int index = -1;

        for (i = 0; i < sName.length; i++) 
        {
            if (sName[i].equals(salesName)) 
            {    //condition if the user entered data matches with the record of rainfall reading array
                index = i;
                System.out.printf("\n%30s", sName[index] + " - " + "sales amount: $" + salesAmt[index] + " With commissions received: $" + sComm[index]);
                System.out.println();
            }
        }
        if (index == -1) 
        {
            System.out.printf("\nThe name you are looking for cannot be found.");
            System.out.println();
        }        
        
    }
    
    //method for searching sales
    public void linearSearchSales (int[] salesAmt, int searchSales, String[] sName)
    {

        int i = 0;
        int index = -1;
        int roundedAmt = 0;
        //condition if the user entered data matches with the record of rainfall reading array
        for (i = 0; i < salesAmt.length; i++) 
        {
            roundedAmt = salesAmt[i]/1000;
            if (roundedAmt <= searchSales) 
            {    
                index = i;
            }
        }
        
        //condition for printing
        if (index == -1) 
        {
            System.out.printf("\nThe sales amount you are looking for cannot be found.");
            System.out.println();
        }
        else
        {
            System.out.println("The following sales persons have less than $" + searchSales*1000);
                for (i = 0; i < salesAmt.length; i++) 
                {
                    roundedAmt = salesAmt[i]/1000;
                    if (roundedAmt < searchSales) 
                    {
                        index = i;
                        System.out.printf("\n%30s", sName[index] + " $" + roundedAmt*1000);
                    }
                }
        }
    }
    
    //method for displaying salespersons lowest, highest, and median sales amount
    public void displayStatistics (int[]salesAmt, String[]sName)
    {
        int position = 0;
        int i = 0;
        double high_sales = salesAmt[0];
        double low_sales = salesAmt[0];
        double median = 0.0;
        
        for (i = 0; i < salesAmt.length; i++) {
            if (salesAmt[i] < low_sales) {
                low_sales = salesAmt[i];        // condition to STORE lowest sales
            }
        }
        for (i = 0; i < salesAmt.length; i++) {
            if (salesAmt[i] == low_sales) {     // condition to CHECK lowest sales
                position = i;
                System.out.print("The sales person that has the lowest sales amount is  " + sName[i] + ", $" + salesAmt[i] + ".");
            }
        }
        
        for (i = 0; i < salesAmt.length; i++) 
        {
            if (salesAmt[i] > high_sales) {
                high_sales = salesAmt[i];        // condition to STORE highest sales
            }
        }
        
        for (i = 0; i < salesAmt.length; i++) 
        {
            if (salesAmt[i] == high_sales) {     // condition to CHECK highest sales
                position = i;
                System.out.print("\nThe sales person that has the highest sales amount is  " + sName[i] + ", $" + salesAmt[i] + ".");
                System.out.println();
            }
        }
        
        //sort sales amount before median
        for (i=salesAmt.length-1; i>0; i--)
    	{   
            for (int j=0; j<i; j++)
            {
            	if (salesAmt[j] > salesAmt[j+1])
            	{
                    int temp=salesAmt[j];
                    salesAmt[j]=salesAmt[j+1];
                    salesAmt[j+1]=temp;
                    
                }
            }
        }
        
        //median condition
        if (salesAmt.length % 2 == 0)
        {
            median = ((double)salesAmt[salesAmt.length/2] + (double)salesAmt[salesAmt.length/2 - 1])/2;
            System.out.println("The median sales amount is $" + median);
        }
        else
        {
            median = (double) salesAmt[salesAmt.length/2];
            System.out.println("The median sales amount is $" + median);
        }
        
        
    }
    
    public static void main(String[] args) 
    {
        Scanner reader = new Scanner (System.in);
        SalesDataManager sdm = new SalesDataManager();
        
    	int loop = sdm.spersonName.length;
            
            for (int j=0; j< loop; j++)
            {
                System.out.println (sdm.centerFormat +  sdm.centerHeader1);
                System.out.println (sdm.divider);
                System.out.println(sdm.option1 + "\n" + sdm.option2 + "\n" + sdm.option3 + "\n" + sdm.option4 + "\n" + sdm.option5 + "\n" + sdm.option6 + "\n" + sdm.option7 + "\n" + sdm.option8);
                System.out.println("\nEnter options 1 - 8: ");
                sdm.menuOption = reader.nextInt();
                reader.nextLine();
                if ((j==0) && (sdm.menuOption != 1))
                {
                    System.out.println("Input required first. Please choose option 1.");
                }
                else
                    sdm.displayMenu();
                
            }
            
            
    }

    
}



