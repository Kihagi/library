package controller;

import common.Constants;
import common.Helper;
import dao.HibernateServerDAOImpl;
import dao.RecordSetDAO;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by mathenge on 11/21/2017.
 */
@WebServlet(name = "LoginController", urlPatterns = {"/sign-in"})
public class LoginController extends HttpServlet implements Serializable {

    private static final long serialVersionUID = 1L;
    private RecordSetDAO<User> userDAO = new HibernateServerDAOImpl<User>(User.class);

//    @EJB
//    private Helper helper;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        boolean proceed;

        if(session != null)
        {
            try {
                if((session.getAttribute(Constants.LOGIN).equals(true)))
                {
                    response.sendRedirect("member");
                    proceed = false;
                }
                else
                    proceed = true;
            } catch (NullPointerException npe) {

                proceed = true;

            }
        } else {
            proceed = true;
        }

        if(proceed)
        {
            request.getRequestDispatcher("login.jsp").forward(request, response);

        }
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        PrintWriter out = response.getWriter();

        String firstField = "username";
        String secondField = "password";
        String link = "dashboard";

        User user = userDAO.doubleCriteria( firstField, request.getParameter("username"), secondField,
                Helper.hash(request.getParameter("password")), User.CREATED_AT, 1, 0, 1).get(0);
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "User Found: " + user.getUsername());

        if(user != null && user.getUsername()!= null)
        {
            try {
                session.setAttribute(Constants.USER, user.getUsername());
                session.setAttribute(Constants.UID, user.getId());
                session.setAttribute(Constants.LOGIN, true);
                out.write(Helper.result(true, link).toString());

            } catch (NullPointerException npje) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, npje);
                out.write(Helper.result(false, "Login Failed!<br />Invalid username and/or password<br />Please try again").toString());
            }

        } else {
            out.write(Helper.result(false, "Login Failed!<br />Invalid username and/or password<br />Please try again").toString());
        }

    }
}
