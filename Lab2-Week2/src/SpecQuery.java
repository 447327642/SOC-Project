import java.util.ArrayList;

public class SpecQuery {
	
	public DatabaseOperations myDatabase;
	
	public SpecQuery() throws Exception{
		ArrayList<Article> res = MyDOMParser.MyParser();
		
		myDatabase = new DatabaseOperations();
		myDatabase.openConnection();
		myDatabase.createTables();
		for(Article temp : res){
			myDatabase.addArticle(temp);
		}
	}
	// given an author and get all his or her co-author
	public ArrayList<String> getCoAuthor(String author){
		ArrayList<String> res = new ArrayList<String>();
		int[] articleID = myDatabase.returnAllArticleID(author);
		res = myDatabase.getAllAuthor(articleID);
		System.out.println("Co-authors :      ");
		for(String temp : res){
			System.out.println(temp);
		}
		System.out.println("--------------------------");
		return res;
	}
	// given two authors and find out if they all co-authors
	public boolean weatherCoAuthor(String author1, String author2){
		boolean res = false;
		ArrayList<String> set1 = getCoAuthor(author1);
		ArrayList<String> set2 = getCoAuthor(author2);
		for(String temp : set1){
			if(set2.contains(temp)){
				res = true;
			}
		}
		System.out.println("Author :  " + author1 + "  and  " + author2 + "   are  co-authors ?   " + res );
		System.out.println("--------------------------");
		return res;
	}
	// given an exact paper name and get all the publication details
	public Article getPaperByTitle(String title){
		Article article = new Article();
		article = myDatabase.getExactPaper(myDatabase.returnArticleID(title));
		System.out.println("Paper Searched:     ");
		System.out.println(article);
		System.out.println("--------------------------");
		return article;
	}
	// given an author name and find all the publication details
	public ArrayList<Article> getAllPublications(String author){
		ArrayList<Article> res = myDatabase.getAllPaper(myDatabase.returnAllArticleID(author));
		for(Article temp : res){
			System.out.println("Publications:      ");
			System.out.println(temp);
		}
		System.out.println("--------------------------");
		return res;
	}
	// given an keyword and find all related publications
	public ArrayList<String> searchKeyWord(String keyWord){
		ArrayList<String> res = myDatabase.getRelatedPaper(keyWord);
		for(String temp : res){
			System.out.println("Related Paper:     " + temp);
		}
		System.out.println("--------------------------");
		return res;
	}
}
