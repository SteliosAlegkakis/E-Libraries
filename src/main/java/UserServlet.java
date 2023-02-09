import database.tables.EditLibrarianTable;
import database.tables.EditStudentsTable;
import mainClasses.Librarian;
import mainClasses.Student;

import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import static database.tables.EditLibrarianTable.databaseToLibrarian;
import static database.tables.EditStudentsTable.databaseToStudent;

@WebServlet(name = "UserServlet", value = "/UserServlet")
public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        System.out.println("doGet");
        if(request.getParameter("request").equals("login")) login(request,response);
        else if(request.getParameter("request").equals("logout")) logout(request,response);
        else if(request.getParameter("request").equals("isLoggedIn")) isLoggedIn(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doPost");
        register(request,response);
    }

    private void register(HttpServletRequest request, HttpServletResponse response){

    }

    public void login(HttpServletRequest request, HttpServletResponse response){
        System.out.println("login");
        HttpSession session=request.getSession();
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            Librarian lb = databaseToLibrarian(username,password);
            Student su = databaseToStudent(username, password);
            if(lb == null && su == null) response.setStatus(403);
            else {
                session.setAttribute("loggedIn",username);
                session.setAttribute("password",password);
                System.out.println("logged in:"+session.getAttribute("loggedIn"));
                int activeUsers=0;
                if(request.getServletContext().getAttribute("activeUsers")!=null){
                    activeUsers=(int) request.getServletContext().getAttribute("activeUsers");
                    request.getServletContext().setAttribute("activeUsers",activeUsers+1);
                    System.out.println("activeUsers: "+request.getServletContext().getAttribute("activeUsers"));
                    if(lb!=null)response.setStatus(202);
                    if(su!=null)response.setStatus(201);
                }
                else{
                    request.getServletContext().setAttribute("activeUsers",1);
                    System.out.println("active users:"+request.getServletContext().getAttribute("activeUsers"));
                    if(lb!=null)response.setStatus(202);
                    if(su!=null)response.setStatus(201);
                }
            }
        }
        catch (SQLException e) {System.out.println("SQLException in login");}
        catch (ClassNotFoundException e) {System.out.println("ClassNotFoundException in login");}
    }

    public void logout(HttpServletRequest request, HttpServletResponse response){
        System.out.println("logout");
        HttpSession session = request.getSession();
        System.out.println(session.getAttribute("loggedIn"));
        if(session.getAttribute("loggedIn")!=null){
            session.invalidate();
            int activeUsers=(int) request.getServletContext().getAttribute("activeUsers");
            request.getServletContext().setAttribute("activeUsers",activeUsers-1);
            response.setStatus(200);
        }
        else{
            response.setStatus(403);
        }
    }

    public void isLoggedIn(HttpServletRequest request, HttpServletResponse response){
        System.out.println("isLoggedIn");
        HttpSession session=request.getSession();
        if(session.getAttribute("loggedIn")!=null){
            response.setStatus(200);
        }
        else{
            response.setStatus(403);
        }

    }

    private void getInfo(HttpServletRequest request, HttpServletResponse response){

    }
}
