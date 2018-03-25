package controller;

import common.Constants;
import common.Helper;
import dao.HibernateServerDAOImpl;
import dao.RecordSetDAO;
import model.Book;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created by mathenge on 11/22/2017.
 */
@WebServlet(name = "Dashboard", urlPatterns = {"/dashboard"})
public class DashboardController extends HttpServlet implements Serializable {
    private static final long serialVersionUID = 1L;

    private RecordSetDAO<Book> booksDAO = new HibernateServerDAOImpl<Book>(Book.class);

//    @EJB
//    private Helper helper;

    private String getSessKey(HttpServletRequest req, String param)
    {
        HttpSession session = req.getSession(false);
        return session != null ? Helper.toString(session.getAttribute(param)) : null;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /* Check if user is already authenticated */
        HttpSession session = request.getSession(false);

        int booksCount = 0;
        int booksBorrowed = 0;
        int booksAvailable = 0;

        try {
            booksCount = booksDAO.totalNoOfBooks();
            System.out.println("Total No of books ::::::::::  " + booksCount);
            booksAvailable = booksDAO.booksAvailable();
            System.out.println("No of books Available ::::::::::  " + booksAvailable);
            booksBorrowed = booksDAO.booksBorrowed();
            System.out.println("No of books Borrowed ::::::::::  " + booksBorrowed);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        request.setAttribute("count", booksCount);
        request.setAttribute("available", booksAvailable);
        request.setAttribute("borrowed", booksBorrowed);
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);

        if(session != null)
        {
            String user = this.getSessKey(request, Constants.USER).trim();
            request.getRequestDispatcher("dashboard.jsp").forward(request, response);
        }else {
            response.sendRedirect(getServletContext().getContextPath() + "/sign-in");
        }

    }
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

    }
}
