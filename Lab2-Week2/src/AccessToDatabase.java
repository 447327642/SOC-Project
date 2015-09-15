
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;


public class AccessToDatabase {
	private String JDBCDriver;
	private String User;
	private String Password;
	private String DBUrl;
	private Connection connection;
	private Properties props;
	
	public AccessToDatabase(String propsFile){
		props= new Properties();
		FileInputStream in = null;
		try {
			in = new FileInputStream(propsFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			props.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		} 
		this.JDBCDriver = props.getProperty("JDBCDriver");
		this.User = props.getProperty("User");
		this.Password = props.getProperty("Password");
		this.DBUrl = props.getProperty("DBUrl");
		System.out.println("AccessToDatabase Successfully !");
	}
	
	public boolean openConnection(){
		try {
			Class.forName(JDBCDriver);
		} catch (ClassNotFoundException e) {
			System.out.println("Class Not Found!");
			return false;
		}
		try {
			connection = DriverManager.getConnection(DBUrl, User, Password);
		} catch (SQLException e) {
			System.out.println("Cannot Make a Connection");
			return false;
		}
		return true;				
	}
	
	// used to execute create, drop table and insert, updata and delete
	public int executeCDIUD(String statement){
		Statement stat;
		int result;
		try {
			stat = connection.createStatement();
			result = stat.executeUpdate(statement);
			stat.close();
			return result;
		} catch (SQLException e) {
			System.out.println("Cannot Connect");
			e.printStackTrace();
			return -1;
		}
	}
	
	public String returnResult(String statement, String oneCol, int choice){
		Statement stat;
		Object result = null;
		String result2 = new String();
		try {
			stat = connection.createStatement();
			ResultSet r = stat.executeQuery(statement);
			if(r.equals(null)){
				System.out.println("No result.");
			}else{
				while(r.next()){
					if(choice==0)
						result = r.getObject(oneCol);
					if(choice==1){
						result2 += r.getString(oneCol) + "@";
					}
				}
			}
			stat.close();
			r.close();
			if(choice==0)
				return result.toString();
			else
				return result2.substring(0, result2.length() - 1);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("cannot retrieve result from DB!");
			return null;
		}
	}
	
	public String returnResult(String statement, int oneCol){
		Statement stat;
		Object result = null;
		try {
			stat = connection.createStatement();
			ResultSet r = stat.executeQuery(statement);
			if(r.equals(null)){
				System.out.println("No result.");
			}else{
				while(r.next()){
					result = r.getObject(oneCol);
				}
			}
			stat.close();
			r.close();
			return result.toString();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("cannot retrieve result from DB!");
			return null;
		}
	}
	
	
	
	public Properties getProps(){
		return props;
	}
	
	public void Close() throws SQLException{
		connection.close();
	}
	
}
