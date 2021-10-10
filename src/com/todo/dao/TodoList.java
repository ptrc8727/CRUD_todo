package com.todo.dao;

import java.util.*;

import com.todo.service.TodoSortByDate;
import com.todo.service.TodoSortByName;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import com.todo.service.DbConnect;

public class TodoList {
	Connection conn;

	public TodoList() {
		this.conn = DbConnect.getConnection();
	}
	
	public void closeC() {
		
		DbConnect.closeConnection();
		
	}

	public void importData(String filename) {
		
		try {
			
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			String sql = "insert into list (title, memo, category, current_date, Due_date)"+" values (?,?,?,?,?);";
			int records = 0;
			while((line=br.readLine())!=null) {
				
				StringTokenizer st = new StringTokenizer(line, "##");
				String category = st.nextToken();
				String title = st.nextToken();
				String description = st.nextToken();
				String due_date = st.nextToken();
				String current_date = st.nextToken();
				
				PreparedStatement pstmt = this.conn.prepareStatement(sql);
				pstmt.setString(1, title);
				pstmt.setString(2, description);
				pstmt.setString(3, category);
				pstmt.setString(4, current_date);
				pstmt.setString(5, due_date);
				int count = pstmt.executeUpdate();
				if(count>0) records++;
				pstmt.close();
				
			}
			
			System.out.println(records+" records read!");
			br.close();
			
		}catch(Exception e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	public int addItem(TodoItem t) {
		
		String sql = "insert into list (title, memo, category, current_date, Due_date)"+" values (?,?,?,?,?);";
		PreparedStatement pstmt;
		int count = 0;
		
		try {
			
			pstmt = this.conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCategory());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDuedate());
			count = pstmt.executeUpdate();
			pstmt.close();
			
		}catch(SQLException e) {
			
			e.printStackTrace();
			
		}
		
		return count;
		
	}

	public void deleteItem(int ind) {
		
		String sql = "delete from list where id=?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			
			pstmt = this.conn.prepareStatement(sql);
			pstmt.setInt(1,ind);
			count = pstmt.executeUpdate();
			pstmt.close();
			
		}catch(SQLException e) {
			
			e.printStackTrace();
			
		}
		
	}

	public int editItem(TodoItem t, int ii) {
		
		String sql = "update list set title=?, memo=?, category=?, current_date=?, due_date=?" + " where id =?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			
			pstmt = this.conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCategory());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDuedate());
			pstmt.setInt(6, ii);
			count = pstmt.executeUpdate();
			pstmt.close();
			
		}catch(SQLException e) {
			
			e.printStackTrace();
			
		}
		
		return count;
		
	}
	
	public int len() {
		
		List<TodoItem> list = getList();
		return list.size();
		
	}
	
	public TodoItem get(int a) {
		
		List<TodoItem> list = getList();
		return list.get(a);
		
	}

	public ArrayList<TodoItem> getList() {
		
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		
		try {
			stmt = this.conn.createStatement();
			String sql = "SELECT * FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				
				TodoItem t = new TodoItem(title, description, category, due_date);
				t.setCurrent_date(current_date);
				list.add(t);
				
			}
			
			stmt.close();
			
		}catch(SQLException e) {
			
			e.printStackTrace();
			
		}
		
		return list;
		
	}
	
	public ArrayList<TodoItem> getListKey(String key){
		
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		key = "%"+key+"%";
		try {
			
			String sql = "SELECT * FROM list WHERE title like ? or memo like ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,key);
			pstmt.setString(2,key);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				
				TodoItem t = new TodoItem(title, description, category, due_date);
				t.setCurrent_date(current_date);
				list.add(t);
				
			}
			
			pstmt.close();
			
		}catch(SQLException e) {
			
			e.printStackTrace();
			
		}
		
		return list;
		
	}
	
	public ArrayList<TodoItem> getCate(String str){
		
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		try {
			
			String sql = "SELECT * FROM list WHERE category=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,str);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				
				TodoItem t = new TodoItem(title, description, category, due_date);
				t.setCurrent_date(current_date);
				list.add(t);
				
			}
			
		}catch(SQLException e){
			
			e.printStackTrace();
			
		}
		return list;
	}
	
	public ArrayList<String> getCategories(){
		
		int count =0;
		ArrayList<String> list = new ArrayList<String>();
		Statement stmt;
		try {
			
			stmt = conn.createStatement();
			String sql = "SELECT DISTINCT category FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				
				count++;
				String qq = rs.getString("category");
				System.out.print(qq );
				list.add(qq);
				
			}
			
		}catch(SQLException e) {
			
			e.printStackTrace();
			
		}
		return list;
	}
	
	public ArrayList<TodoItem> getOrderedList(String ord, int ord_i){
		
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list ORDER BY "+ord;
			if(ord_i==0) {
				
				sql+="desc";
				
			}
			
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				
			int id = rs.getInt("id");
			String category = rs.getString("category");
			String title = rs.getString("title");
			String description = rs.getString("memo");
			String due_date = rs.getString("due_date");
			String current_date = rs.getString("current_date");
				
				TodoItem t = new TodoItem(title, description, category, due_date);
				t.setCurrent_date(current_date);
				list.add(t);
				
			}
			
		}catch(SQLException e){
			
			e.printStackTrace();
			
		}
		return list;
	}
	
	public int getCount() {
		
		return this.getList().size();
		
	}

	public void sortByName() {
		List<TodoItem> list = getList();
		Collections.sort(list, new TodoSortByName());

	}

	public void listAll() {
		List<TodoItem> list = getList();
		System.out.println("\n"
				+ "inside list_All method\n");
		for (TodoItem myitem : list) {
			System.out.println(myitem.getTitle() + myitem.getDesc());
		}
	}
	
	public void reverseList() {
		List<TodoItem> list = getList();
		Collections.reverse(list);
	}

	public void sortByDate() {
		List<TodoItem> list = getList();
		Collections.sort(list, new TodoSortByDate());
	}

	public int indexOf(TodoItem t) {
		List<TodoItem> list = getList();
		return list.indexOf(t);
	}

	public Boolean isDuplicate(String title) {
		List<TodoItem> list = getList();
		for (TodoItem item : list) {
			if (title.equals(item.getTitle())) return true;
		}
		return false;
	}
	
	public int indexOf(String str) {
		
		List<TodoItem> list = getList();
		return list.indexOf(str);
		
	}
	
	
}
