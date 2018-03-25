package controller;

import dao.HibernateServerDAOImpl;
import dao.RecordSetDAO;
import model.Book;
import model.Employee;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "Employee", urlPatterns = {"/employee"})
public class EmployeeController extends HttpServlet implements Serializable {
    private static final long serialVersionUID = 1L;

    private RecordSetDAO<Employee> employeeDAO = new HibernateServerDAOImpl<Employee>(Employee.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Employee> employees = new ArrayList<>();
        employees = employeeDAO.readAll(Book.CREATED_AT, 0, 0, 1000);
        for (Employee employee : employees) {
            System.out.println("Employee found: " +
                    employee.getId() + ", " + employee.getNames()+ ", " + employee.getEmail());
        }
        request.setAttribute("employees", employees);
        request.getRequestDispatcher("employees.jsp").forward(request, response);
    }
}
