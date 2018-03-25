package controller;

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
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "Book", urlPatterns = {"/book"})
public class BookController extends HttpServlet implements Serializable  {

    private static final long serialVersionUID = 1L;

    private RecordSetDAO<Book> booksDAO = new HibernateServerDAOImpl<Book>(Book.class);

    private static final String REQUEST_ACTION = "ACTION";
    private static final String FETCH_BOOK_DETAILS = "FETCH_BOOK_DETAILS";
    private static final String DELETE_BOOK = "DELETE_BOOK";
    private static final String EDIT_BOOK = "EDIT_BOOK";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Book> books = new ArrayList<>();
        books = booksDAO.readAll(Book.CREATED_AT, 0, 0, 1000);
        for (Book book : books) {
            System.out.println("Book found: " +
            book.getId() + ", " + book.getTitle()+ ", " + book.getAuthor());
        }
        request.setAttribute("books", books);
        request.getRequestDispatcher("books.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter(REQUEST_ACTION);
        System.out.println("Action passed ::::::: " + action);

        switch (action) {
            case FETCH_BOOK_DETAILS:
                fetchBookDetails(request, response);
                break;
            case DELETE_BOOK:
                deleteBook(request, response);
                break;
            case EDIT_BOOK:
                editBook(request, response);
                break;
        }
    }

    private void editBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = response.getWriter();

        String title = "";
        String author ="";
        String edition = "";
        String publisher = "";
        String isbn = "";
        String category = "";
        String status = "";

        boolean updatedOrCreate = false;
        String successMessage = "";
        String errorMessage = "";

        try {
            title = request.getParameter("title");
            author = request.getParameter("author");
            edition = request.getParameter("edition");
            publisher = request.getParameter("publisher");
            isbn = request.getParameter("isbn");
            category = request.getParameter("category");
            status = request.getParameter("status");

            if (request.getParameter("type").equalsIgnoreCase("EDIT")) {

                String bookId = request.getParameter("bookId");
                System.out.println("Book ID String ::::::::: " + bookId);
                long book_id = Long.parseLong(bookId);
                System.out.println("Book ID Long::::::> " + book_id);

                errorMessage = "Sorry, something didn't work out right. Couldn't save the book details";
                updatedOrCreate = booksDAO.updateBook(title, author, edition, publisher, isbn, category, status, book_id);
                successMessage = "Book details successfully updated";
            } else if (request.getParameter("type").equalsIgnoreCase("ADD")) {
                errorMessage = "Sorry, something didn't work out right. Couldn't add book details";
                Book book = new Book();
                book.setAuthor(author);
                book.setTitle(title);
                book.setEdition(edition);
                book.setPublisher(publisher);
                book.setIsbn(isbn);
                book.setCategory(category);
                book.setStatus(status);
                booksDAO.create(book);
                updatedOrCreate = true;
                successMessage = "Book details successfully added";
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (updatedOrCreate) {
            out.write(Helper.result(true, successMessage).toString());
        } else {
            out.write(Helper.result(true, errorMessage).toString());
        }
    }

    private void fetchBookDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getParameter("type").equals("EDIT")) {
            String bookId = request.getParameter("bookId");
            System.out.println("Book ID :::::::: " + bookId);

            System.out.println("Type is ::::::::::::: " + request.getParameter("type"));
            List<Book> books = bookList(bookId);
            request.setAttribute("books", books);
        }

        request.setAttribute("book_id", request.getParameter("bookId"));
        request.setAttribute("type", request.getParameter("type"));
        request.getRequestDispatcher("book_details.jsp").forward(request, response);
    }

    private void deleteBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = response.getWriter();

        String bookId = request.getParameter("bookId");
        System.out.println("Book ID :::::::: " + bookId);

        Book book = null;
        boolean deleted = false;
        try {
            book = findBook(bookId);
            booksDAO.delete(book);
            deleted = true;
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            out.write(Helper.result(false, "Error!<br />Book could not be deleted!").toString());
        }

        if (deleted) {
            out.write(Helper.result(true, "Book was successfully deleted!").toString());
        }

    }

    private List<Book> bookList(String bookId) {
        System.out.println("Book ID::::::> " + bookId);

        long book_id = Long.parseLong(bookId);
        System.out.println("Book ID Long::::::> " + book_id);

        List<Book> books = null;
        try {
            List<String> fkFieldName = new ArrayList<String>();
            List<Object> fkFieldVal = new ArrayList<Object>();
            fkFieldName.add(Book.ID);
            fkFieldVal.add(book_id);
            books = booksDAO
                    .readByFields(fkFieldName, fkFieldVal, Book.CREATED_AT, 1, 0, 1);

        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return books;
    }

    private Book findBook(String bookId) {
        System.out.println("Book ID::::::> " + bookId);

        long book_id = Long.parseLong(bookId);
        System.out.println("Book ID Long::::::> " + book_id);

        Book book = null;

        List<Book> books = new ArrayList<>();
        try {
            List<String> fkFieldName = new ArrayList<String>();
            List<Object> fkFieldVal = new ArrayList<Object>();
            fkFieldName.add(Book.ID);
            fkFieldVal.add(book_id);
            books = booksDAO
                    .readByFields(fkFieldName, fkFieldVal, Book.CREATED_AT, 1, 0, 1);

        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }

        if (!books.isEmpty()) {
            book = books.get(0);
            System.out.println("Book found :::::: " + book.getTitle());
        }
        return book;
    }
}
