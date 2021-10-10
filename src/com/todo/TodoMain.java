package com.todo;

import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {
	
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		l.importData("todolist.txt");
		
		
		
		boolean isList = false;
		boolean quit = false;
		do {
			//Menu.displaymenu();
			Menu.prompt();
			System.out.println("command: ");
			
			
			isList = false;
			String cho = sc.nextLine();
			String[] chch = cho.split(" ");
			String choice = chch[0];
			switch (choice) {

			case "add":
				TodoUtil.createItem(l);
				break;
			
			case "del":
				TodoUtil.deleteItem(l);
				break;
				
			case "edit":
				TodoUtil.updateItem(l);
				break;
				
			case "ls":
				TodoUtil.listAll(l);
				break;

			case "ls_name_asc":
				l.sortByName();
				isList = true;
				break;

			case "ls_name_desc":
				l.sortByName();
				l.reverseList();
				isList = true;
				break;
				
			case "ls_date":
				l.sortByDate();
				isList = true;
				break;
				
			case "find":
				System.out.println(chch[1]);
				TodoUtil.find(l, chch[1]);
				break;

			case "ls_cate":
				TodoUtil.listCateAll(l);
				break;
				
			case "find_cate":
				TodoUtil.findCateList(l, chch[1]);
				break;
				
			case "ls_name":
				TodoUtil.listAllO(l, "title", 1);
				break;
				
			case "exit":
				quit = true;
				break;
				
			default:
				System.out.println("please enter one of the above mentioned command");
				break;
			}
			
			if(isList) l.listAll();
		} while (!quit);
		
		TodoUtil.saveList(l, "todolist_0.txt");
		l.closeC();
		
	}
}
