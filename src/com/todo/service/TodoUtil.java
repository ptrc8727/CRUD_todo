package com.todo.service;

import java.util.*;
import java.io.*;
import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList l) {
		
		String cate, title, desc, duedate;
		Scanner sc = new Scanner(System.in);
		
		
		System.out.println("\n"
				+ "========== Create item Section\n"
				+ "enter the category\n");
		
		cate = sc.nextLine();
		
		System.out.println("\n"
				+ "enter the title\n");
		
		title = sc.nextLine();
		if (l.isDuplicate(title)) {
			System.out.printf("title can't be duplicate");
			return;
		}
		
		System.out.println("enter the description");
		desc = sc.nextLine();
		
		System.out.println("enter the duedate(yyyy-MM-dd HH:mm:ss)");
		duedate = sc.nextLine();
		
		TodoItem t = new TodoItem(cate, title, desc, duedate);
		
		if(l.addItem(t)>0)
			System.out.println("추가되었습니다.");
		
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "========== Delete Item Section\n"
				+ "enter the index of item to remove\n"
				+ "\n");
		
		
		int a = sc.nextInt();
		int b = l.len();
		
		
		if (0<a&&a<=b) {
			
					l.deleteItem(a);

		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "========== Edit Item Section\n"
				+ "enter the index of the item you want to update\n"
				+ "\n");
		
		System.out.println("enter the title of the item you want to update\n"
				+ "\n");
		int id = sc.nextInt();
		if (id-1>l.len()) {
			System.out.println("index does not exist");
			return;
		}

		System.out.println("enter the new title of the item");
		String new_title = sc.nextLine().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("title can't be duplicate");
			return;
		}
		
		System.out.println("enter the new category ");
		String new_cate = sc.nextLine().trim();
		
		System.out.println("enter the new description ");
		String new_description = sc.nextLine().trim();
		
		System.out.println("enter the new duedate(yyyy-MM-dd HH:mm:ss) ");
		String new_duedate = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(new_title, new_description, new_cate, new_duedate);
		if(l.editItem(t,id)>0)
			System.out.println("수정되었습니다");
		
	}

	public static void listAll(TodoList l) {
		System.out.println("List every items: ");
		int a = l.getCount();
		System.out.println("total ["+a+"] elements");
		int i=1;
		for (TodoItem item : l.getList()) {
			
			System.out.println(i+item.toString());
			i = i+1;
		}
	}
	
	public static void listAllO(TodoList l, String ord, int ord_i) {
		
		System.out.printf("total index: %d of elements...!\n",l.getCount());
		for(TodoItem item:l.getOrderedList(ord, ord_i)) {
			
			System.out.println(item.toString());
			
		}
		
	}
	
	public static void find(TodoList l, String str) {
		
		int count = 0;
		for (TodoItem item : l.getListKey(str)) {
			
			System.out.println(item.toString());
			count++;
			
		}
				
		System.out.printf("totally %d elements are found...!\n",count);
			
	}
	
	public static void findCateList(TodoList l, String str) {
		
		int count = 0;
		for(TodoItem item:l.getCate(str)) {
			
			System.out.println(item.toString());
			count++;
			
		}
		System.out.printf("\ntotally %d elements are found", count);
	}
	
	public static void listCateAll(TodoList l) {
		
		int count = 0;
		for (String item: l.getCategories()) {
			
			System.out.print(item+" ");
			count++;
			
		}
		System.out.printf("\nthere are %d categories are enrolled...!",count);
	}
	
	public static void saveList(TodoList l,String filename) {
		
		System.out.println("save the List as a file named \'todolist.txt\'");
		
		try (FileWriter f = new FileWriter(filename)) {
			
			for(TodoItem item : l.getList()) {
				
			f.write(item.toSaveString());
			
			}
			
			f.close();
			
		} catch (Exception e) {			
			System.out.println("exception");
		}
		
	}
	
	public static void loadList(TodoList l, String filename) {
		
		try(BufferedReader br = new BufferedReader(new FileReader(filename))) {
			
					String str;
					
					System.out.println("load the List from \'todolist.txt\'");
					
					while ((str = br.readLine()) != null) {
					
						StringTokenizer st = new StringTokenizer(str,"##",false);
						String ct = st.nextToken();
						String t = st.nextToken();
						String d = st.nextToken();
						String c = st.nextToken();
						String dd = st.nextToken();
						TodoItem a = new TodoItem(ct,t,d,dd);
						a.setCurrent_date(c);
						
						l.addItem(a);
						
					}
			
			br.close();
			
			
		}catch(Exception e){
			
			System.out.print("there's no such file.");
			
		}
		
	}
}
