package common;

import dao.HibernateServerDAOImpl;
import dao.RecordSetDAO;
import model.User;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ejb.Stateless;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by mathenge on 11/22/2017.
 */
@Stateless
public class Helper {

    private static RecordSetDAO<User> userDAO = new HibernateServerDAOImpl<User>(User.class);

    private static final String MESSAGE = "message";
    private static final String SUCCESS = "success";

    static List<User> users;


    public static JSONObject result(boolean status, String message)
    {
        JSONObject obj = new JSONObject();
        try {
            obj.put(Helper.SUCCESS, status);
            obj.put(Helper.MESSAGE, message);
        } catch (JSONException je) {
            Logger.getLogger(Helper.class.getName()).log(Level.SEVERE, null, je);
        }
        return obj;
    }

    public static boolean userExists(String username) {
        try {
            List<String> fkFieldName = new ArrayList<String>();
            List<Object> fkFieldVal = new ArrayList<Object>();
            fkFieldName.add(User.USERNAME);
            fkFieldVal.add(username);
            users = userDAO
                    .readByFields(fkFieldName, fkFieldVal, User.CREATED_AT, 1, 0, 1);

        } catch (Exception ex) {
            Logger.getLogger(Helper.class.getName()).log(Level.SEVERE, null, ex);
        }

        return !users.isEmpty() && users != null;
    }

    public long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        System.out.println("Get difference between: " + date2 + " and " + date1);
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }

    public static long getDateDiffStatic(Date date1, Date date2, TimeUnit timeUnit) {
        System.out.println("Get difference between: " + date2 + " and " + date1);
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }

    public boolean isBetween(int value)
    {
        System.out.println("Date difference :::: " + value);
        int min = 0;
        int max = 31;
        System.out.println("isbetwween returned :::: " + ((value > min) && (value <= max)));
        return((value > min) && (value <= max));
    }

    public static boolean isBetweenStatic(int value)
    {
        System.out.println("Date difference :::: " + value);
        int min = 0;
        int max = 31;
        System.out.println("isbetwween returned :::: " + ((value > min) && (value <= max)));
        return((value > min) && (value <= max));
    }

    public static String hash(String password)
    {
        String sha1 = "";
        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(password.getBytes("UTF-8"));
            sha1 = byteToHex(crypt.digest());
        }
        catch(NoSuchAlgorithmException | UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return sha1;
    }
    private static String byteToHex(final byte[] hash)
    {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    public static String convertDate(String date) throws ParseException {

        System.out.println("Date to convert :::::::::: " + date);

        String[] parts = date.split(" ");
        date = parts[0];
        if (date.contains("/")){
            System.out.println(" =========== String contains backslash ==========");
            date = date.replaceAll("/", "-");
        }
        System.out.println("Date before conversion ::::::::: " + date);

        SimpleDateFormat input = new SimpleDateFormat("dd-MM-yyyy");
        Date dateValue = input.parse(date);
        System.out.println("Date Value :::::: " + dateValue);

        SimpleDateFormat output = new SimpleDateFormat("dd/MMM/yyyy");
        String outputDate = output.format(dateValue);
        System.out.println("Final String Value :::::: " + outputDate);

        return outputDate;
    }

    public static Date convertJournalTxDate(String date) throws ParseException {

        System.out.println("Date from B2C Excel :::::::::: " + date);

        SimpleDateFormat input = new SimpleDateFormat("yyy/MM/dd");
        Date dateValue = input.parse(date);
        System.out.println("Date Value :::::: " + dateValue);

        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
        String outputDate = output.format(dateValue);
        System.out.println("Final String Value :::::: " + outputDate);

        return output.parse(outputDate);
    }

    public static Date convertEndDate(String date) throws ParseException {

        System.out.println("Date b4 conversion :::::::::: " + date);

        SimpleDateFormat input = new SimpleDateFormat("yyy/MM/dd");
        Date dateValue = input.parse(date);
        System.out.println("Date Value :::::: " + dateValue);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String outputDate = dateFormat.format(dateValue);
        System.out.println("Final String Value :::::: " + outputDate);

        return dateFormat.parse(outputDate);
    }

    public static String toString(Object o)
    {
        try {
            return o.toString();
        } catch (NullPointerException npe) {
            return "";
        }
    }
}
