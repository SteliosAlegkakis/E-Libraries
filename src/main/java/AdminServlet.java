import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "AdminServlet", value = "/AdminServlet")
public class AdminServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private void login(HttpServletRequest request, HttpServletResponse response){

    }

    private void getStatistics(HttpServletRequest request, HttpServletResponse response){

    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response){

    }
}
