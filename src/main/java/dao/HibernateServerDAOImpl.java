package dao;

import model.Common;
import org.hibernate.*;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Work;
import org.hibernate.type.StringType;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @param <T>
 * @author wesleykirinya
 */
public class HibernateServerDAOImpl<T extends Common> extends HibernateWrapper implements RecordSetDAO<T> {

    protected Class<T> tClass = null;

    public HibernateServerDAOImpl(Class<T> _tClass) {
        tClass = _tClass;
    }

    public void create(T _model) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            long __recID = (Long) session.save(_model);
            _model.setId(__recID);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
    }

    public void update(T _model) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(_model);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
    }

    public void saveOrUpdate(T _model) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.saveOrUpdate(_model);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
    }

    public T read(long id) {
        T __t = null;
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            __t = (T) ((session.get(tClass, id)));
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }

        return __t;
    }

    public boolean delete(T _model) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(_model);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
        return true;
    }

    public List<T> readAll(String _sortField, int _sortDirection,
                           int _startIndex, int _recordCount) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;
        List<T> __records = new ArrayList<T>();
        try {
            tx = session.beginTransaction();

            Criteria crit = session.createCriteria(tClass);
            if (_sortField.length() > 0) {
                if (_sortDirection == 0) {
                    crit = crit.addOrder(Order.asc(_sortField));
                }// end if
                else {
                    crit = crit.addOrder(Order.desc(_sortField));
                }// end else
            }
            __records = crit.setFirstResult(_startIndex)
                    .setMaxResults(_recordCount).list();

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }

        return __records;
    }

    public List<T> fetchPhoneNumbers() {

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;

        List<T> __records = new ArrayList<T>();

        try {
            tx = session.beginTransaction();
            String sql = "SELECT phone_number FROM tbl_qbuser_details WHERE user_type IS NULL OR user_type = '';";
            SQLQuery query = session.createSQLQuery(sql);
            __records = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }

        return __records;
    }

    public List<T> fetchCustomersWithoutId() {

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;

        List<T> __records = new ArrayList<T>();

        try {
            tx = session.beginTransaction();
            String sql = "SELECT user_name FROM tbl_qbuser_details WHERE user_type='Borrower' AND (qb_id IS NULL OR qb_id = '')";
            SQLQuery query = session.createSQLQuery(sql);
            __records = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }

        return __records;
    }

    public List<T> fetchLendersWithoutId() {

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;

        List<T> __records = new ArrayList<T>();

        try {
            tx = session.beginTransaction();
            String sql = "SELECT user_name FROM tbl_qbuser_details WHERE user_type='Lender' AND (qb_id IS NULL OR qb_id = '')";
            SQLQuery query = session.createSQLQuery(sql);
            __records = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }

        return __records;
    }

    public List<T> fetchUsers() {

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;

        List<T> __records = new ArrayList<T>();

        try {
            tx = session.beginTransaction();
            String sql = "SELECT user FROM tbl_transaction_details;";
            SQLQuery query = session.createSQLQuery(sql);
            __records = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }

        return __records;
    }

    public void updateUserType(String phoneNumber, String customerType, String webappId) {

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            String sql = "Update tbl_qbuser_details SET user_type = :customer_type, webapp_id = :webapp_id where phone_number = :phone_number";
            SQLQuery query = session.createSQLQuery(sql);
            query.setParameter("customer_type", customerType);
            query.setParameter("phone_number", phoneNumber);
            query.setParameter("webapp_id", webappId);
            query.executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }

    }

    public void updateC2BUserTypeAndName(String phoneNumber, String customerType, String userName, String webappId) {

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            String sql = "Update tbl_qbuser_details SET user_type = :customer_type, user_name = :userName, " +
                    "webapp_id = :webapp_id where phone_number = :phone_number";
            SQLQuery query = session.createSQLQuery(sql);
            query.setParameter("customer_type", customerType);
            query.setParameter("userName", userName);
            query.setParameter("phone_number", phoneNumber);
            query.setParameter("webapp_id", webappId);
            query.executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }

    }

    public void updatePenaltyDateAndAmount(Date date, int amount, long loanId) {

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            String sql = "Update tbl_penalties SET penalty_amount = :penaltyAmount, date_updated = :dateUpdated where loan_id = :loan_id";
            SQLQuery query = session.createSQLQuery(sql);
            query.setParameter("penaltyAmount", amount);
            query.setParameter("dateUpdated", date);
            query.setParameter("loan_id", loanId);
            query.executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }

    }

    public void updateInterestDateAndAmount(Date date, BigDecimal amount, String webappId) {

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            String sql = "Update tbl_interest SET total_interest = :interestAmount, date_updated = :dateUpdated where webapp_id = :webappId";
            SQLQuery query = session.createSQLQuery(sql);
            query.setParameter("interestAmount", amount);
            query.setParameter("dateUpdated", date);
            query.setParameter("webappId", webappId);
            query.executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }

    }

    public void updateInterestSubmitDate(Date date, BigDecimal amount, String entryId) {

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            String sql = "Update tbl_interest SET total_interest = :interestAmount, date_submitted = :dateSubmitted where qb_entry_id = :entryId";
            SQLQuery query = session.createSQLQuery(sql);
            query.setParameter("interestAmount", amount);
            query.setParameter("dateSubmitted", date);
            query.setParameter("entryId", entryId);
            query.executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }

    }

    public void updatePenaltiesSubmitDate(Date date, BigDecimal amount, String entryId) {

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            String sql = "Update tbl_penalties SET penalty_amount = :penaltyAmount, date_submitted = :dateSubmitted where qb_entry_id = :entryId";
            SQLQuery query = session.createSQLQuery(sql);
            query.setParameter("penaltyAmount", amount);
            query.setParameter("dateSubmitted", date);
            query.setParameter("entryId", entryId);
            query.executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }

    }

    public void updateWhtDateAndAmount(Date date, BigDecimal whtamount, String webappId) {

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            String sql = "Update tbl_withholdingtax SET wht_amount = :whtAmount, date_updated = :dateUpdated where webapp_id = :webappId";
            SQLQuery query = session.createSQLQuery(sql);
            query.setParameter("whtAmount", whtamount);
            query.setParameter("dateUpdated", date);
            query.setParameter("webappId", webappId);
            query.executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }

    }

    public void updateQbCustomerDisplayNameAndID(String displayName, String qbId, String phoneNo) {

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            String sql = "Update tbl_customers SET qb_id = :quickbooks_id,  qb_name = :display_name where customer_phoneno = :phone_no AND customer_type='Borrower'";
            SQLQuery query = session.createSQLQuery(sql);
            query.setParameter("quickbooks_id", qbId);
            query.setParameter("display_name", displayName);
            query.setParameter("phone_no", phoneNo);
            query.executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }

    }

    public void updateCoreQbCustomerID(String phoneNo, String qbId, String displayName) {

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            String sql = "Update tbl_customers SET qb_id = :quickbooks_id, qb_name = :qb_name where customer_phoneno = :phone_no AND customer_type='Borrower'";
            SQLQuery query = session.createSQLQuery(sql);
            query.setParameter("quickbooks_id", qbId);
            query.setParameter("phone_no", phoneNo);
            query.setParameter("qb_name", displayName);
            query.executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }

    }

    public void updateQbCustomerID(String displayName, String qbId) {

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            String sql = "Update tbl_qbuser_details SET qb_id = :quickbooks_id where user_name = :display_name AND user_type='Borrower'";
            SQLQuery query = session.createSQLQuery(sql);
            query.setParameter("quickbooks_id", qbId);
            query.setParameter("display_name", displayName);
            query.executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }

    }

    public void updateQbCustomerIDByPhone(String phoneNumber, String qbId) {

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            String sql = "Update tbl_qbuser_details SET qb_id = :quickbooks_id where phone_number = :phoneNumber AND user_type='Borrower'";
            SQLQuery query = session.createSQLQuery(sql);
            query.setParameter("quickbooks_id", qbId);
            query.setParameter("phoneNumber", phoneNumber);
            query.executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }

    }

    public void updateQbLenderID(String displayName, String qbId) {

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            String sql = "Update tbl_qbuser_details SET qb_id = :quickbooks_id where user_name = :display_name AND user_type='Lender'";
            SQLQuery query = session.createSQLQuery(sql);
            query.setParameter("quickbooks_id", qbId);
            query.setParameter("display_name", displayName);
            query.executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }

    }

    public void updateQbLenderIDByPhone(String phone, String qbId) {

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            String sql = "Update tbl_qbuser_details SET qb_id = :quickbooks_id where phone_number = :phoneNumber AND user_type='Lender'";
            SQLQuery query = session.createSQLQuery(sql);
            query.setParameter("quickbooks_id", qbId);
            query.setParameter("phoneNumber", phone);
            query.executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }

    }

    public void updateCoreQbLenderID(String displayName, String qbId, String phoneNo) {

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            String sql = "Update tbl_customers SET qb_id = :quickbooks_id, qb_name = :display_name where customer_phoneno = :phone_no AND customer_type='Lender'";
            SQLQuery query = session.createSQLQuery(sql);
            query.setParameter("quickbooks_id", qbId);
            query.setParameter("display_name", displayName);
            query.setParameter("phone_no", phoneNo);
            query.executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }

    }

    public List<T> fetchUserType(String phoneNumber, Session session) {

        List<T> results = new ArrayList<T>();

        try {

            String sql = "SELECT customer_type FROM tbl_customers WHERE customer_phoneno = :phone_number";
            SQLQuery query = session.createSQLQuery(sql);
            query.setParameter("phone_number", phoneNumber);
            results = query.list();

        } catch (HibernateException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }

        return results;
    }

    public List<T> fetchUserWebappId(String phoneNumber, Session session) {

        List<T> results = new ArrayList<T>();

        try {

            String sql = "SELECT customer_id FROM tbl_customers WHERE customer_phoneno = :phone_number";
            SQLQuery query = session.createSQLQuery(sql);
            query.setParameter("phone_number", phoneNumber);
            results = query.list();

        } catch (HibernateException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }

        return results;
    }


    public List<T> fetchUserAccountNo(String phoneNumber, Session session) {

        List<T> results = new ArrayList<T>();

        try {

            String sql = "SELECT customer_identification FROM tbl_customers WHERE customer_phoneno = :phone_number";
            SQLQuery query = session.createSQLQuery(sql);
            query.setParameter("phone_number", phoneNumber);
            results = query.list();

        } catch (HibernateException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }

        return results;
    }


    public void updateTransactionStatus(long transactionId) {

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            String sql = "Update tbl_transaction_details SET submitted = :status where id = :txn_id";
            SQLQuery query = session.createSQLQuery(sql);
            query.setParameter("status", 1);
            query.setParameter("txn_id", transactionId);
            query.executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }

    }

    public boolean updateBook(String title, String author, String edition, String publisher, String isbn, String category,
                              String status, long bookId) {

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            String sql = "Update tbl_books SET title = :title, author = :author, edition = :edition, publisher = :publisher, " +
                    "isbn = :isbn, category = :category, status = :status where id = :book_id";
            SQLQuery query = session.createSQLQuery(sql);
            query.setParameter("title", title);
            query.setParameter("author", author);
            query.setParameter("edition", edition);
            query.setParameter("publisher", publisher);
            query.setParameter("isbn", isbn);
            query.setParameter("category", category);
            query.setParameter("status", status);
            query.setParameter("book_id", bookId);
            query.executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
        return true;
    }

    public int booksAvailable() {

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;

        BigInteger results = BigInteger.ZERO;

        int resultsResponse = 0;

        try {
            tx = session.beginTransaction();

            String sql = "SELECT count(*) FROM library.tbl_books where status='Available'";
            SQLQuery query = session.createSQLQuery(sql);
            System.out.println(":::::::::::::::::::::: Starting query execution :::::::::::::::::");

            results = (BigInteger)query.uniqueResult();

            resultsResponse = results.intValue();

            System.out.println("Result returned :::::: " + resultsResponse);
            System.out.println(":::::::::::::::::::::: Finished query execution :::::::::::::::::");
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }

        return resultsResponse;
    }

    public int booksBorrowed() {

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;

        BigInteger results = BigInteger.ZERO;

        int resultsResponse = 0;

        try {
            tx = session.beginTransaction();

            String sql = "SELECT count(*) FROM library.tbl_books where status='Borrowed'";
            SQLQuery query = session.createSQLQuery(sql);
            System.out.println(":::::::::::::::::::::: Starting query execution :::::::::::::::::");

            results = (BigInteger)query.uniqueResult();

            resultsResponse = results.intValue();

            System.out.println("Result returned :::::: " + resultsResponse);
            System.out.println(":::::::::::::::::::::: Finished query execution :::::::::::::::::");
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }

        return resultsResponse;
    }

    public int totalNoOfBooks() {

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;

        BigInteger results = BigInteger.ZERO;

        int resultsResponse = 0;

        try {
            tx = session.beginTransaction();

            String sql = "SELECT count(*) FROM library.tbl_books";
            SQLQuery query = session.createSQLQuery(sql);
            System.out.println(":::::::::::::::::::::: Starting query execution :::::::::::::::::");

            results = (BigInteger)query.uniqueResult();

            resultsResponse = results.intValue();

            System.out.println("Result returned :::::: " + resultsResponse);
            System.out.println(":::::::::::::::::::::: Finished query execution :::::::::::::::::");
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }

        return resultsResponse;
    }

    public List<T> readByFields(List<String> _fkFieldName,
                                List<Object> _fkFieldVal_, String _sortField, int _sortDirection,
                                int _startIndex, int _recordCount) {
        //System.out.println("Session is ::::::::::::: " + sessio);
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;
        List<T> __records = new ArrayList<T>();
        try {
            tx = session.beginTransaction();

            Criteria crit = session.createCriteria(tClass);
            Criterion fkCriterion[] = new Criterion[_fkFieldName.size()];
            for (int i = 0; i < _fkFieldName.size(); ++i) {
                String __fieldNameIndex = _fkFieldName.get(i);
                Object __fkFieldValIndex = _fkFieldVal_.get(i);
                fkCriterion[i] = Restrictions.eq(__fieldNameIndex, __fkFieldValIndex);
            }
            Conjunction __conjunction = Restrictions.and(fkCriterion);
            crit.add(__conjunction);

            if (_sortField.length() > 0) {
                if (_sortDirection == 0) {
                    crit = crit.addOrder(Order.asc(_sortField));
                }// end if
                else {
                    crit = crit.addOrder(Order.desc(_sortField));
                }// end else
            }
            __records = crit.setFirstResult(_startIndex)
                    .setMaxResults(_recordCount).list();

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }

        return __records;
    }

    public List<T> readByFieldsExistTx(List<String> _fkFieldName,
                                List<Object> _fkFieldVal_, String _sortField, int _sortDirection,
                                int _startIndex, int _recordCount) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;
        List<T> __records = new ArrayList<T>();
        try {
            tx = session.beginTransaction();

            Criteria crit = session.createCriteria(tClass);
            Criterion fkCriterion[] = new Criterion[_fkFieldName.size()];
            for (int i = 0; i < _fkFieldName.size(); ++i) {
                String __fieldNameIndex = _fkFieldName.get(i);
                Object __fkFieldValIndex = _fkFieldVal_.get(i);
                fkCriterion[i] = Restrictions.eq(__fieldNameIndex, __fkFieldValIndex);
            }
            Conjunction __conjunction = Restrictions.and(fkCriterion);
            crit.add(__conjunction);

            if (_sortField.length() > 0) {
                if (_sortDirection == 0) {
                    crit = crit.addOrder(Order.asc(_sortField));
                }// end if
                else {
                    crit = crit.addOrder(Order.desc(_sortField));
                }// end else
            }
            __records = crit.setFirstResult(_startIndex)
                    .setMaxResults(_recordCount).list();

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }

        return __records;
    }

    public List<T> readByFields(List<String> _fkFieldName,
                                List<Object> _fkFieldVal, String _sortField, int _sortDirection) {
        return readByFields(_fkFieldName, _fkFieldVal, _sortField, _sortDirection, 0, 100);
    }

    public List<T> doubleCriteria(String firstField, String firstValue, String secondField, String secondValue, String _sortField, int _sortDirection,
                                  int _startIndex, int _recordCount) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;
        List<T> __records = new ArrayList<T>();
        try {
            tx = session.beginTransaction();

            Criteria crit = session.createCriteria(tClass);
            crit.add(Restrictions.eq(firstField, firstValue));
            crit.add(Restrictions.eq(secondField, secondValue));

            if (_sortField.length() > 0) {
                if (_sortDirection == 0) {
                    crit = crit.addOrder(Order.asc(_sortField));
                }// end if
                else {
                    crit = crit.addOrder(Order.desc(_sortField));
                }// end else
            }
            __records = crit.setFirstResult(_startIndex)
                    .setMaxResults(_recordCount).list();

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }

        return __records;
    }

    public void clear() {
        List<T> __records = readAll(Common.CREATED_AT, 0, 0, 100);
        for (T _record : __records) {
            delete(_record);
        }
    }
}
