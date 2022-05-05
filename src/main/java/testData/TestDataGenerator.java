package testData;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class TestDataGenerator {
    public Logger logger = LogManager.getLogger(TestDataGenerator.class);
    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");
    private static final Pattern EDGESDHASHES = Pattern.compile("(^-|-$)");

    public static String getRandomUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public static String getcurrentDate() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date today = Calendar.getInstance().getTime();
        return df.format(today);
    }

    public static String getcurrentDateUTC() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date today = Calendar.getInstance().getTime();
        return df.format(today);
    }

    public static String getcurrentDateEST() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("US/Eastern"));
        Date today = Calendar.getInstance().getTime();
        return df.format(today);
    }

    public static String getcurrentDateEmailDateFormat() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date today = Calendar.getInstance().getTime();
        return df.format(today);
    }


    public static String getFormattedDate(String date){
        System.out.println(date);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println( df.format(date));
        return df.format(date);
    }

    public static String getUniqueDate() {
        DateFormat df = new SimpleDateFormat("dd_MMM_HH_mm");
        Date today = Calendar.getInstance().getTime();
        return df.format(today);
    }

    public static String getcurrentDatemmddyyyy() {
        DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        Date today = Calendar.getInstance().getTime();
        return df.format(today);
    }

    public static String getRandomCode() {
        Random random = new Random();
        return String.format("%04d", random.nextInt(10000));
    }

    public static String getRandomCodee() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000));
    }

    public static String genRandomSubEntityValue() {
        String generatedString = RandomStringUtils.randomAlphabetic(5) + "-" + RandomStringUtils.randomAlphabetic(5);
        return generatedString.toUpperCase();
    }

    public static String genRandomClassBusinessValue() {
        String generatedString = RandomStringUtils.randomAlphabetic(4) + "_" + RandomStringUtils.randomAlphabetic(4)
                + "_" + RandomStringUtils.randomAlphabetic(4) + "_" + RandomStringUtils.randomAlphabetic(4);
        return generatedString.toLowerCase();
    }


    public static String getRandomAlphabeticString(int i) {
        String generatedString = RandomStringUtils.randomAlphabetic(i);
        return generatedString;
    }

    public static String getRandomNumericString(int i) {
        String generatedString = RandomStringUtils.randomNumeric(i);
        return generatedString;
    }

    public static String getRandomAlphaNumericString(int i) {
        String generatedString = RandomStringUtils.randomAlphanumeric(i);
        return generatedString;
    }

    public static String getRandomPhone() {
        Random generator = new Random();
        String strippedNum;
        int num1 = 0;
        int num2 = 0;
        int num3 = 0;

        num1 = generator.nextInt(600) + 100;//numbers can't include an 8 or 9, can't go below 100.
        num2 = generator.nextInt(641) + 100;//number has to be less than 742//can't go below 100.
        num3 = generator.nextInt(8999) + 1000; // make numbers 0 through 9 for each digit.//can't go below 1000.

        String string1 = Integer.toString(num1);
        return string1 + "-" + num2 + "-" + num3;
    }

    public static String generateRandomEmail(int length) {
        String allowedChars = "abcdefghijklmnopqrstuvwxyz";
        return RandomStringUtils.random(length, allowedChars) + "@yopmail.com";
    }

    public static String getPastDate(int days) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -days);
        return df.format(cal.getTime());
    }

    public static String getFutureDate(int days) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, days);
        return df.format(cal.getTime());
    }

    public static String getUniqueNumber() {
        Date d = new Date();
        return "" + d.getTime();
    }

    public static String getRandomPassword() {
        return getRandomAlphabeticString(3).toUpperCase() + getRandomAlphabeticString(3).toLowerCase()
                + "@" + getRandomNumericString(5);
    }

    public static int getRandomNumber(int maxLimit) {
        Random rand = new Random();
        return rand.nextInt(maxLimit);
    }

    public static String getGroupId(){
        return getRandomAlphabeticString(10)+"-"+getRandomAlphabeticString(10)+"-"+getRandomAlphabeticString(7);
    }

    @Test
    public void terst123(){
        getcurrentDate();
//        System.out.println(getRandomAlphabeticString(10));
//        System.out.println(getRandomAlphaNumericString(10));
//        System.out.println(RandomStringUtils.random(10));
//        System.out.println(getRandomPhone());
//        System.out.println(generateRandomEmail(10));
//        System.out.println(getPastDate(2));
//        System.out.println(getFutureDate(2));

        System.out.println(getUniqueNumber());
    }

}
