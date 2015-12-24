import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class BankersAlgo {

	static int[] available;
	static int[][] max;
	static int[][] allocation;
	static int[][] need;

	public static void release ( int pNum, int r1, int r2, int r3 ) {

		allocation[pNum][0] -= r1;
		allocation[pNum][1] -= r2;
		allocation[pNum][2] -= r3;

		available[0] += r1;
		available[1] += r2;
		available[2] += r3;
	}
	
	static void viewStatus(){
		
		System.out.println("Available (A, B, C): \n" + Arrays.toString(available));
		int len = max.length;
		
		System.out.println("\nAllocation(A, B, C)" );
		for ( int i=0; i<len ; i++ ){
			
			System.out.println ( i + ") " + Arrays.toString(allocation[i]));
		}
		
		System.out.println("\nNeed(A, B, C)" );
		for ( int i=0; i<len ; i++ ){
			
			System.out.println ( i + ") " + Arrays.toString(need[i]));
		}
	}
	
	public static boolean safe ( int[] tempAvailable, int[][] tempAllocation, int[][] tempNeed ) {

		int[] work = tempAvailable ;
		boolean[] finish = new boolean[tempAllocation.length] ;
		boolean flag = false ;
		boolean result = true ;
		
		Arrays.fill ( finish, false ) ;
	
		while ( !flag ) {

			flag = true;

			for ( int i=0 ; i<finish.length ; i++ ) {

				if ( tempNeed[i][0] <= work[0] && tempNeed[i][1] <= work[1]
						&& tempNeed[i][2] <= work[2] && !finish[i] ) {

					flag = false;
					finish[i] = true;

					work[0] += tempAllocation[i][0];
					work[1] += tempAllocation[i][1];
					work[2] += tempAllocation[i][2];
				}
			}
		}
		for ( int i=0 ; i<finish.length ; i++ ) {

			result &= finish[i] ;
		}
		
		return result ;
	}

	public static boolean request ( int pNum, int r1, int r2, int r3 ) {

		int[] preAvailable = available;
		int[][] preAllocation = allocation;
		int[][] preNeed = need;

		if ( r1 <= need[pNum][0] && r2 <= need[pNum][1] && r3 <= need[pNum][2] ) {

			if ( r1 <= available[0] && r2 <= available[1] && r3 <= available[2] ) {

				preAvailable[0] -= r1;
				preAvailable[1] -= r2;
				preAvailable[2] -= r3;

				preAllocation[pNum][0] += r1;
				preAllocation[pNum][1] += r2;
				preAllocation[pNum][2] += r3;

				preNeed[pNum][0] -= r1;
				preNeed[pNum][1] -= r2;
				preNeed[pNum][2] -= r3;

				if ( safe ( preAvailable, preAllocation, preNeed ) ) {

					available = preAvailable;
					need = preNeed;
					allocation = preAllocation;

					return true;
				}
			}
		}

		return false;
	}

	public static int[] readOne ( int length , String fileName ){
		
		int [] res = new int [length];
		Scanner scanner;
		int i = 0 ;
		
		try {
			
			scanner = new Scanner ( new File( fileName ) );
			
			while ( scanner.hasNextInt() ){
				
			   res[i++] = scanner.nextInt();
			}
		} 
		
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return res ;
	}

	public static int[][] readTwo ( int length , String fileName ){
		
		int [][] res = new int [length][3];
		Scanner scanner;
		int i = 0 , j = 0 ;
		
		try {
			
			scanner = new Scanner ( new File( fileName ) );
			
			while ( scanner.hasNextInt() ){
				
			   res[i][j] = scanner.nextInt();
			   j = ( j + 1) % 3 ;
			   
			   if ( j == 0 )
				   i ++ ;
			}
			/*for ( int k=0 ; k<5 ; k++ )
				System.out.println( Arrays.toString(res[k]) );*/
		} 
		
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return res ;
	}
	
	public static void main(String[] args) {
		
		//readTwo(5, "allocation.txt") ;
		
		available = readOne(3, "available.txt") ;

		allocation = readTwo(5, "allocation.txt") ;

		max = readTwo(5, "max.txt") ;

		need = readTwo(5, "need.txt") ;
		
		Scanner input = new java.util.Scanner(System.in);
		
		while ( true ) {
			
			String str = input.nextLine();
			
			if ( str.equals("*") ){
				
				viewStatus();
			} 
			
			else if ( str.equals("Quit") ){
				
				System.exit(0);
			} 
			
			else {
				
				String[] chars = str.split(" ");
				
				if ( chars[0].equals("RQ") ) {
					
					request ( Integer.parseInt(chars[1]),
							Integer.parseInt(chars[2]),
							Integer.parseInt(chars[3]),
							Integer.parseInt(chars[4]) 
					);
				} 
				else if ( chars[0].equals("RL") ) {
					
					release ( Integer.parseInt(chars[1]),
							Integer.parseInt(chars[2]),
							Integer.parseInt(chars[3]),
							Integer.parseInt(chars[4]) 
					);
				}
			}
		}
	}
}
