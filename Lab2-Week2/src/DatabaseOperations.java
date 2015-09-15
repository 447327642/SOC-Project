import java.util.ArrayList;
import java.util.HashSet;

public class DatabaseOperations extends AccessToDatabase{
	
	public DatabaseOperations(){
		super("/Users/gongbailiang/Documents/workspace/Lab2-Week2/DatabaseCommand");
	}	
	
	public void createTables(){
		executeCDIUD("DROP DATABASE IF EXISTS lab2");
		executeCDIUD("CREATE DATABASE IF NOT EXISTS lab2");
		executeCDIUD("USE lab2");
		String createTable_article = getProps().getProperty("ArticleTable");
		String createTable_author = getProps().getProperty("AuthorTable");
		if(executeCDIUD(createTable_article)==-1){
			System.out.println("Create article table failed !");
			return;
		}
		if(executeCDIUD(createTable_author)==-1){
			System.out.println("Create author table failed !");
			return;
		}
		
		System.out.println("Table created succesfully !");
	}	
	
	public int addArticle(Article article){
		executeCDIUD("USE lab2");
		article.title = article.title.replace("\'", "\'\'");
		String insertArticle = "INSERT IGNORE INTO " + "Article" + " (Mdate, Keyword, Title, Pages, Year, Volume, Journal, Number, Url, Ee) " 
				+ "VALUES ("
				+ "'" + article.mdate + "'" + ", " 
				+ "'" + article.key + "'" + ", " 
				+ "'" + article.title + "'" + ", "
				+ "'" + article.pages + "'" + ", "
				+ "'" + article.year + "'" + ", "
				+ "'" + article.volume + "'" + ", "
				+ "'" + article.journal + "'" + ", " 
				+ "'" + article.number + "'" + ", " 
				+ "'" + article.url + "'" + ", " 
				+ "'" + article.ee + "'" + ");";
		int result = executeCDIUD(insertArticle);
		if(result==-1) return -1;
		
		for(int i = 0; i < article.author.size(); i++){
			article.author.set(i, article.author.get(i).replace("\'", "\'\'")); 
			String insertAuthor =  "INSERT IGNORE INTO " + "Author" + "(AuthorName, ArticleId) " +
                    "VALUES('" + article.author.get(i) + "'," + "'" + returnArticleID(article.title) + "'" + ");";
			int result1 = executeCDIUD(insertAuthor);
			if(result1==-1) return -1;
		}
		return 0;
		
		
	}
	
	
	public Article getExactPaper(int aticleID){
		executeCDIUD("USE lab2");
		Article temp = new Article();
		String queryArticle = "SELECT * FROM Article WHERE ArticleId=" + aticleID + ";";
		temp.mdate = returnResult(queryArticle, 2);
		temp.key = returnResult(queryArticle, 3);
		temp.title = returnResult(queryArticle, 4);
		temp.pages = returnResult(queryArticle, 5);
		temp.year = returnResult(queryArticle, 6);
		temp.volume = returnResult(queryArticle, 7);
		temp.journal = returnResult(queryArticle, 8);
		temp.number = returnResult(queryArticle, 9);
		temp.url = returnResult(queryArticle, 10);
		temp.ee = returnResult(queryArticle, 11);
		
		queryArticle = "SELECT * FROM Author WHERE ArticleID=" + aticleID + ";"; 
		String[] author = returnResult(queryArticle, "AuthorName", 1).split("@");
		for(String curAuthor : author){
			temp.author.add(curAuthor);
		}
		return temp;
	}
	public ArrayList<Article> getAllPaper(int[] articleID){
		ArrayList<Article> res = new ArrayList<Article>();
		for(int curID : articleID){
			Article temp = new Article();
			String queryArticle = "SELECT * FROM Article WHERE ArticleId=" + curID + ";";
			temp.mdate = returnResult(queryArticle, 2);
			temp.key = returnResult(queryArticle, 3);
			temp.title = returnResult(queryArticle, 4);
			temp.pages = returnResult(queryArticle, 5);
			temp.year = returnResult(queryArticle, 6);
			temp.volume = returnResult(queryArticle, 7);
			temp.journal = returnResult(queryArticle, 8);
			temp.number = returnResult(queryArticle, 9);
			temp.url = returnResult(queryArticle, 10);
			temp.ee = returnResult(queryArticle, 11);
			
			queryArticle = "SELECT * FROM Author WHERE ArticleID=" + curID + ";"; 
			String[] author = returnResult(queryArticle, "AuthorName", 1).split("@");
			for(String curAuthor : author){
				temp.author.add(curAuthor);
			}
			res.add(temp);
		}
		for(Article temp : res){
			System.out.println(temp.toString());
		}
		return res;
	}
	public ArrayList<String> getAllAuthor(int[] articleID){
		ArrayList<String> res = new ArrayList<String>();
		HashSet<String> temp = new HashSet<String>();
		for(int curID : articleID){
			String queryArticle = "SELECT * FROM Author WHERE ArticleID=" + curID + ";"; 
			String[] author = returnResult(queryArticle, "AuthorName", 1).split("@");
			for(String curAuthor : author){
				temp.add(curAuthor);
			}
		}
		res.addAll(temp);
		return res;
	}
	public ArrayList<String> getRelatedPaper(String title){
		executeCDIUD("USE lab2");
		int[] ID = returnRelatedArticleID(title);
		ArrayList<String> res = new ArrayList<String>();
		for(int cur : ID){
			String queryTitle = "SELECT Title FROM Article WHERE ArticleId=" + cur + ";";
			res.add(returnResult(queryTitle, "Title", 0));
		}
		for(String temp : res){
			System.out.println(temp);
		}
		return res;
		
	}
	
	public String getAllArticle(){
		executeCDIUD("USE lab2");
		String query = "SELECT Title FROM Article;";
		String result = returnResult(query, "Title", 1);
		return result;
	}
	public String getAuthor(String title){
		executeCDIUD("USE lab2");
		int articleID = returnArticleID(title);
		String query = "SELECT AuthorName" + " FROM "
					+ "Author" + " WHERE ArticleId=" + "'" + articleID + "'" + ";";
		String result = returnResult(query, "AuthorName", 1);
		return result;
	}
	public int returnArticleID(String title){
		String query = "SELECT ArticleID" + " FROM "
				+ "Article" + " WHERE Title=" + "'" + title + "'" + ";";
		
		String ID = returnResult(query, "ArticleId", 0);
		if(ID.equals(null) || ID.length()==0){
			System.out.println("cannot retrieve");
			return -1;
		}else{
			return Integer.parseInt(ID);
		}
	}
	public int[] returnRelatedArticleID(String subtitile){
		String query = "SELECT ArticleID" + " FROM "
				+ "Article" + " WHERE Title LIKE " + "'%" + subtitile + "%'" + ";";
		
		String ID = returnResult(query, "ArticleId", 1);
		if(ID.equals(null) || ID.length()==0){
			System.out.println("cannot retrieve");
			int[] res = {-1, -1}; 
			return res;
		}else{
			String[] temp = ID.split("@");
			int size = temp.length;
			int[] res = new int[size];
			for(int i = 0; i < size; i++){
				res[i] = Integer.parseInt(temp[i]);
			}
			return res;
		}
	}
	public int[] returnAllArticleID(String author){
		String query = "SELECT ArticleID" + " FROM "
				+ "Author" + " WHERE AuthorName=" + "'" + author + "'" + ";";
		
		String ID = returnResult(query, "ArticleId", 1);
		if(ID.equals(null) || ID.length()==0){
			System.out.println("cannot retrieve");
			int[] res = {-1, -1}; 
			return res;
		}else{
			String[] temp = ID.split("@");
			int size = temp.length;
			int[] res = new int[size];
			for(int i = 0; i < size; i++){
				res[i] = Integer.parseInt(temp[i]);
			}
			return res;
		}
	}
	
}
