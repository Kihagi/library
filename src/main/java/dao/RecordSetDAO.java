package dao;

import org.hibernate.Session;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface RecordSetDAO<T> {

    public void create(T _model);

    public T read(long id);

    public void update(T _model);

    public void saveOrUpdate(T _model);

    public void updateUserType(String phoneNumber, String customerType, String webappId);

    public void updateC2BUserTypeAndName(String phoneNumber, String customerType, String userName, String webappId);

    public void updatePenaltyDateAndAmount(Date date, int amount, long loanId);

    public void updateInterestDateAndAmount(Date date, BigDecimal amount, String webappId);

    public void updateInterestSubmitDate(Date date, BigDecimal amount, String entryId);

    public void updatePenaltiesSubmitDate(Date date, BigDecimal amount, String entryId);

    public void updateWhtDateAndAmount(Date date, BigDecimal whtamount, String webappId);

    public void updateQbCustomerID(String displayName, String qbId);

    public void updateQbCustomerIDByPhone(String phoneNumber, String qbId);

    public void updateCoreQbCustomerID(String phoneNo, String qbId, String displayName);

    public void updateQbCustomerDisplayNameAndID(String displayName, String qbId, String phoneNo);

    public void updateQbLenderID(String displayName, String qbId);

    public void updateQbLenderIDByPhone(String phone, String qbId);

    public void updateCoreQbLenderID(String displayName, String qbId, String phoneNo);

    public boolean delete(T _model);

    public List<T> readAll(String _sortField, int _sortDirection,
                           int _startIndex, int _recordCount);

    public List<T> fetchPhoneNumbers();

    public List<T> fetchCustomersWithoutId();

    public List<T> fetchLendersWithoutId();

    public List<T> fetchUsers();

    public List<T> readByFields(List<String> _fkFieldName,
                                List<Object> _fkFieldVal, String _sortField, int _sortDirection,
                                int _startIndex, int _recordCount);

    public List<T> readByFields(List<String> _fkFieldName,
                                List<Object> _fkFieldVal, String _sortField, int _sortDirection);

    public List<T> doubleCriteria(String firstField, String firstValue, String secondField, String secondValue, String _sortField, int _sortDirection,
                                  int _startIndex, int _recordCount);

    public void clear();

    public boolean updateBook(String title, String author, String edition, String publisher, String isbn, String category,
                              String status, long bookId);

    public int totalNoOfBooks();
    public int booksBorrowed();
    public int booksAvailable();
}
