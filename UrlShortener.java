package cpsc_476_Project1;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class UrlShortener
 */
@WebServlet(name="UrlShortener",
			urlPatterns={"/url","/short/*"})
public class UrlShortener extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    Map<String,String> urlDatabase = new HashMap<>();
    
    public UrlShortener() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
    	
		if(request.getSession().getAttribute("username") == null)
        {
            response.sendRedirect("login");
            return;
        }
		
		
		String longUrlId = "";
		
		if(request.getRequestURL().toString().contains("/short/"))
		{
		
		String shortUrlId = request.getRequestURL().toString();
		
		System.out.println(" request URI :" +shortUrlId);
						
		
		if(urlDatabase.containsValue(shortUrlId))
		{	
			for(String id : urlDatabase.keySet())
			{
				System.out.println("id = "+id);
				if(urlDatabase.get(id).equals(shortUrlId))
				{
					longUrlId = id;
					
					break;
				}
			}
		}
		}
		
		if(!longUrlId.isEmpty())
		{
			System.out.println("Long Url = "+longUrlId);
			response.sendRedirect(longUrlId);		
		    return;
		}
		
		
		System.out.println(request.getSession().getAttribute("username"));
		request.setAttribute("urlNull", false);
		request.setAttribute("shortURL", "false");
		
		request.getRequestDispatcher("/WEB-INF/jsp/view/urlShortener.jsp").forward(request, response);
		
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String longUrl = request.getParameter("longUrl");
		
		
		if( longUrl != null && !longUrl.isEmpty())
		{	
			try
			{
				//Checks if Entered long Url is valid URL(contains valid protocol like http,https; or not) 
				//otherwise throws MalformedURLException
				
				new URL(longUrl);
			}
			catch (Exception e) {
				//if the url doesn't have valid protocol, below statement will append http to it 
				longUrl = "http://"+longUrl;
			}
			
			String shortUrl = "";
			String randomString = ShortURL.generateRandom();
			if(randomString != null && !randomString.isEmpty())
			{
				
				if(urlDatabase.containsKey(longUrl))
				{
					shortUrl = urlDatabase.get(longUrl);
				}
				else
				{
					shortUrl = generateShortUrl(randomString, request);
					urlDatabase.put(longUrl, shortUrl);
				}
				
			}
			request.setAttribute("urlNull", false);
			request.setAttribute("shortURL", shortUrl);
			request.getRequestDispatcher("/WEB-INF/jsp/view/urlShortener.jsp").forward(request, response);
		}
		else
		{
			request.setAttribute("urlNull", true);
			request.getRequestDispatcher("/WEB-INF/jsp/view/urlShortener.jsp").forward(request, response);
		}
		
	}
	
	//This will create shortURL and checks if it is already present in database in that case create another one. 
	
	public String generateShortUrl(String randomString,HttpServletRequest request)
	{
		String domainName[] = request.getRequestURL().toString().split("/", 5);
		String shortUrl = domainName[0] +"//" + domainName[2] +"/"+domainName[3]+"/short/"+ randomString;
		//String shortUrl = domainName[0] +"//foo.bar/"+ randomString;
		if(urlDatabase.containsValue(shortUrl))
		{
			String randomString1 = ShortURL.generateRandom();
			generateShortUrl(randomString1, request);
		}
		return shortUrl;
	}

}
