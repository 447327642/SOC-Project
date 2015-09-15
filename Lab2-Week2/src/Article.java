import java.util.ArrayList;

public class Article {
	
	String mdate = "";
	String key = new String();
	ArrayList<String> author = new ArrayList<String>();
	String title = new String();
	String pages = new String();
	String year = new String();
	String volume = new String();
	String journal = new String();
	String number = "";
	String url = new String();
	String ee = new String();
	

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("mdate:  " + mdate + "\n");
		sb.append("key:  " + key + "\n");
		sb.append("author:  ");
		
		for(String temp : author){
			sb.append(temp + "  ");
		}
		sb.append("\n");
		
		sb.append("title:  " + title + "\n");
		sb.append("pages:  " + pages + "\n");
		sb.append("year:  " + year + "\n");
		sb.append("volume:  " + volume + "\n");
		sb.append("journal:  " + journal + "\n");
		sb.append("number:  " + number + "\n");
		sb.append("url:  " + url + "\n");
		sb.append("ee:  " + ee + "\n");
		sb.append("------------------------------" + "\n");
		return sb.toString();
	}
}
