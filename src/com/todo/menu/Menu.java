package com.todo.menu;
import java.util.*;
public class Menu {
	
	static Scanner sc = new Scanner(System.in);

    public static void displaymenu()
    {
        System.out.println();
        System.out.println("1. Add a new item ( add )");
        System.out.println("2. Delete an existing item ( del )");
        System.out.println("3. Update an item  ( edit )");
        System.out.println("4. List all items ( ls )");
        System.out.println("5. sort the list by name ( ls_name_asc )");
        System.out.println("6. sort the list by name ( ls_name_desc )");
        System.out.println("7. sort the list by date ( ls_date )");
        System.out.println("8. sort the list by category ( ls_cate )");
        System.out.println("9. find elements ( find ... )");
        System.out.println("10. find elements by category( find_cate ... )");
        System.out.println("11. exit (Or press escape key to exit)");
        System.out.println("Enter your choice >");
    }
    
    public static void prompt(){
    	
    	String ih;
    	System.out.println();
        System.out.println("to see manual type the word \'help\',else press any key");
    	
		ih = sc.nextLine();
		if(ih.equals("help")) {
			
			displaymenu();
			
		}
    	
    }
}
