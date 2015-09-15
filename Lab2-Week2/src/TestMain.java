
import javax.jws.WebService;

import java.util.ArrayList;

import javax.jws.WebMethod;
import javax.jws.WebParam;

@WebService()
public class TestMain {
	SpecQuery test;
	public TestMain() throws Exception{
		test = new SpecQuery();
	}
	@WebMethod(action = "weatherCoAuthor")
	public boolean weatherCoAuthor(@WebParam(name = "author1") String author1, @WebParam(name = "author2") String author2) {
		
		return test.weatherCoAuthor(author1, author2);
	}

	@WebMethod(action = "getPaperByTitle")
	public String getPaperByTitle(@WebParam(name = "title") String title) {
		return test.getPaperByTitle(title).toString();
	}
	@WebMethod(action = "getAllPublications")
	public String getAllPublications(@WebParam(name = "authorName") String authorName) {
		ArrayList<Article> res = test.getAllPublications(authorName);
		StringBuilder sb = new StringBuilder();
		for(Article temp : res){
			sb.append(temp.toString());
			sb.append("\n");
		}
		return sb.toString();
	}
	@WebMethod(action = "searchKeyWord")
	public String searchKeyWord(@WebParam(name = "keyword") String keyword) {
		ArrayList<String> res = test.searchKeyWord(keyword);
		StringBuilder sb = new StringBuilder();
		for(String temp : res){
			sb.append(temp);
			sb.append("\n");
		}
		return sb.toString();
	}
	@WebMethod(action = "getCoAuthor")
	public String getCoAuthor(@WebParam(name = "authorName") String authorName) {
		return test.getCoAuthor(authorName).toString();
	}
}