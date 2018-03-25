package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "tbl_borrow")
public class Borrow extends Common {

    @Column(name = "book_id")
    private String bookId = "";

    @Column(name = "employee_id")
    private String employeeId = "";

    @Column(name = "date_borrowed")
    private Date dateBorrowed;

    @Column(name = "date_returned")
    private Date dateReturned;

    @Column(name = "condition_borrowed")
    private String conditionBorrowed = "";

    @Column(name = "condition_returned")
    private String conditionReturned = "";

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Date getDateBorrowed() {
        return dateBorrowed;
    }

    public void setDateBorrowed(Date dateBorrowed) {
        this.dateBorrowed = dateBorrowed;
    }

    public Date getDateReturned() {
        return dateReturned;
    }

    public void setDateReturned(Date dateReturned) {
        this.dateReturned = dateReturned;
    }

    public String getConditionBorrowed() {
        return conditionBorrowed;
    }

    public void setConditionBorrowed(String conditionBorrowed) {
        this.conditionBorrowed = conditionBorrowed;
    }

    public String getConditionReturned() {
        return conditionReturned;
    }

    public void setConditionReturned(String conditionReturned) {
        this.conditionReturned = conditionReturned;
    }
}
