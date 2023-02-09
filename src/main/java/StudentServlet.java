import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import database.tables.*;
import mainClasses.*;

@WebServlet(name = "StudentServlet", value = "/StudentServlet")
public class StudentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getParameter("request").equals("getBooks")) getBooks(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private void update(HttpServletRequest request, HttpServletResponse response){

    }

    public void getBooks(HttpServletRequest request, HttpServletResponse response){
        System.out.println("getBooks");

        EditBooksTable ebt = new EditBooksTable();
        String title = request.getParameter("book_title");
        String genre = request.getParameter("genre");

        try{
            PrintWriter out = response.getWriter();
            if(title!=null){
                Book book = ebt.databaseToBook(title);
                out.println(ebt.bookToJSON(book)+"|");
                System.out.println(ebt.bookToJSON(book));
                response.setStatus(200);
            }
            if(genre!=null){
                ArrayList<Book> books ;
                String book;

                if(genre.equals("All")) books = ebt.databaseToBooks();
                else books = ebt.databaseToBooks(genre);

                for(int i=0;i<books.size();i++){
                    book = ebt.bookToJSON(books.get(i));
                    out.println(book+"|");
                    System.out.println(book);
                }
                response.setStatus(200);

            }

        } catch (IOException e) {
            System.err.println("IOException");
            response.setStatus(500);
        } catch (SQLException e) {
            System.err.println("SQLException");
            response.setStatus(500);
        } catch (ClassNotFoundException e) {
            System.err.println("ClassNotFoundException");
            response.setStatus(500);
        }
    }

    private void borrowBook(HttpServletRequest request, HttpServletResponse response){

    }

    private void getBorrowings(HttpServletRequest request, HttpServletResponse response){

    }

    private void reviewBook(HttpServletRequest request, HttpServletResponse response){

    }

    private void notifications(HttpServletRequest request, HttpServletResponse response){

    }
}
