package controller;

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

/**
 * Created by mathenge on 11/22/2017.
 */
@WebServlet(name = "RegisterController", urlPatterns = {"/register"})
public class RegisterController extends HttpServlet implements Serializable {
    private static final long serialVersionUID = 1L;
    private RecordSetDAO<User> userDAO = new HibernateServerDAOImpl<User>(User.class);

//    @EJB
//    private Helper helper;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            request.getRequestDispatcher("register.jsp").forward(request, response);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        System.out.println(":::::::: POST request received :::::::::");
        PrintWriter out = response.getWriter();

        String sessId = request.getSession().getId();
        // Check validity and consistency of the data.
        if ( sessId==null)
        {
            response.sendError( HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Browser must support session cookies." );
            return;
        }

        HttpSession session = request.getSession(true);
        session.invalidate();

        if(!Helper.userExists(request.getParameter("email"))) {

            User user = new User();
            user.setUsername(request.getParameter("email"));
            user.setPassword(Helper.hash(request.getParameter("password")));
            userDAO.create(user);
            boolean proceed = true;

            if (proceed) {
                out.write(Helper.result(true, "<strong>Registration Successful</strong><br /> Congratulations! Your account has been created on the portal.").toString());
            } else {
                out.write(Helper.result(false, "<strong>Registration Failed!</strong><br /> Please contact Administrator").toString());
            }

        } else {
            out.write(Helper.result(false, "<strong>Registration Failed!</strong><br /> You are already registered").toString());

        }

    }
}
