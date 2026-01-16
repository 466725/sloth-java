package com.utilities;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This utility class is prepared keeping in mind the checks required on the
 * string for automation tests.
 * <p>
 * <b>PLEASE DO NOT MAKE CHANGE TO ANY METHOD WITHOUT ANALIZING THE IMPACT OF
 * THE CHANGE TO EXISTING AUTOMATION TESTS</b>
 * </P>
 */
public class StringUtils {
    protected final static Logger logger = LogManager.getLogger(StringUtils.class.getName());

    /**
     * Check the string is null
     *
     * @param stringToCheck
     * @return true if string is a NULL
     */
    public static boolean isNullValue(String stringToCheck) {
        return (stringToCheck == null);
    }

    /**
     * Remove inputed array of strings from an input string Eg. inputStr =
     * "Hello,\n\t how are you?"\n strarray[] = { "\t", "\n", " " } return will be
     * "Hello,howareyou?" Helps for comparing strings of different formats Can also
     * replace specific characters
     *
     * @param inputStr
     * @return
     */
    public static String stringFilter(String inputStr, String[] strarray) {
        for (String c : strarray) {
            inputStr = inputStr.replaceAll(c, "");
        }
        return inputStr;
    }

    /**
     * Checks for both NULL and EMPTY string and returns true if either is true.
     *
     * @param stringToCheck
     * @return true if string is NULL or is a empty string
     */
    public static boolean isEmpty(String stringToCheck) {
        return (stringToCheck == null || stringToCheck.trim().length() == 0);
    }

    /**
     * Checks for both NULL and EMPTY string and returns true if both are NOT true.
     *
     * @param stringToCheck
     * @return true if string is not NULL and is not a empty string
     */
    public static boolean isNotEmpty(String stringToCheck) {
        return !isEmpty(stringToCheck);
    }

    /**
     * Check the string is blank ("") but not null
     *
     * @param stringToCheck
     * @return true if string is blank and not NULL
     */
    public static boolean isBlankNotNull(String stringToCheck) {
        if (stringToCheck == null) {
            return false;
        } else {
            return "".equals(stringToCheck.trim());
        }
    }

    /**
     * Trim the spaces before/in/after a string
     *
     * @param str : string to be trimmed
     * @return string without spaces and new line characters
     */
    public static String trimSpaces(String str) {
        if (isNotEmpty(str)) {
            return str.replace(" ", "").replace("\n", "").replace("\u00a0", "");
        } else {
            return str;
        }
    }

    /**
     * Compose a string with all the elements of an array separated by a ","
     *
     * @param array
     * @return
     */
    public static String arrayToString(String[] array) {
        // check null
        if (array == null)
            return null;
        // check blank
        if (array.length == 0)
            return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; ++i) {
            if (sb.length() > 0)
                sb.append(",");
            sb.append(array[i]);
        }
        return sb.toString();
    }

    /**
     * @param str
     * @return
     */
    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
        return false;
    }

    /**
     * Use to find the index of the character which appears Nth in the string
     *
     * @param str : String used for search (e.g. "this is the test")
     * @param c   : character to search (e.g. "i")
     * @param n   : times of appearance (e.g. "2")
     * @return the index of the character which appears Nth (e.g. 5)
     */
    public static int indexOfNth(String str, char c, int n) {
        if (str == null) {
            return -1;
        }
        int pos = str.indexOf(c, 0);
        while (n-- > 1 && pos != -1)
            pos = str.indexOf(c, pos + 1);
        return pos;
    }

    /**
     * Use to compare all elements of two string arrays equals or not by ignoring
     * the case and order
     *
     * @param stringArr1
     * @param stringArr2
     * @return
     */
    public static boolean equalsIgnoreCaseAndOrder(String[] stringArr1, String[] stringArr2) {
        if (stringArr1 == null || stringArr2 == null) {
            return false;
        }
        if (stringArr1.length == 0 || stringArr2.length == 0) {
            return false;
        }
        if (stringArr1.length != stringArr2.length) {
            return false;
        }
        boolean stringMatch = true;
        for (int j = 0; j < stringArr1.length; j++) {
            boolean elementFound = false;
            for (int k = 0; k < stringArr2.length; k++) {
                if (stringArr1[j].equalsIgnoreCase(stringArr2[k])) {
                    elementFound = true;
                    break;
                }
            }
            if (!elementFound) {
                stringMatch = false;
                break;
            }
        }
        return stringMatch;
    }

    /**
     * Use to compare all elements of two string arrays equals or not by ignoring
     * the case and order
     *
     * @param stringArr1 : No duplicate elements in the array
     * @param stringArr2 : No duplicate elements in the array
     * @return
     */
    public static boolean equalsIgnoreCaseAndOrderForNonDuplicateArrays(String[] stringArr1, String[] stringArr2) {
        if (checkDuplicatesInArrayIgnoreCase(stringArr1) || checkDuplicatesInArrayIgnoreCase(stringArr2)) {
            return false;
        }
        return equalsIgnoreCaseAndOrder(stringArr1, stringArr2);
    }

    /**
     * Use to check if there are duplicate elements in a String array
     *
     * @param strArr
     * @return
     */
    public static boolean checkDuplicatesInArrayIgnoreCase(String[] strArr) {
        boolean duplicate = false;
        if (strArr == null) {
            return false;
        }
        if (strArr.length > 0) {
            for (int j = 0; j < strArr.length; j++) {
                for (int k = 0; k < strArr.length; k++) {
                    if (j != k && strArr[k].trim().equalsIgnoreCase(strArr[j].trim())) {
                        duplicate = true;
                        break;
                    }
                }
            }
        }
        return duplicate;
    }

    /**
     * returns a single lower case letter as String
     *
     * @return String
     */
    public static String getRandomLetter() {
        Random r = new Random();
        char c = (char) (r.nextInt(26) + 'a');
        return Character.toString(c);
    }

    /**
     * returns a string of random lower case characters of the given length
     *
     * @param length
     * @return Random String
     */
    public static String getRandomString(int length, boolean specialCharacter) {
        StringBuffer buf = new StringBuffer();
        String specialChars = "/*!@#$%^&*()\"{}_[]|\\?/<>,.";
        int spLen = specialChars.length();
        Random r = new Random();
        boolean specialCharPicked = false;
        for (int i = 0; i < length; i++) {
            if (specialCharacter && ((i == length - 1 && specialCharPicked == false) || r.nextBoolean())) {
                buf.append(specialChars.charAt(r.nextInt(spLen)));
                specialCharPicked = true;
            } else {
                buf.append(getRandomLetter());
            }
        }
        return buf.toString();
    }

    /**
     * To parse a comma separated string (with certain format) and return a hashmap
     * object. The format of the csv string is K1:V1, K2:V2, K3:V3, K4:V4, ... (V1,
     * V2, ... Vn are optional) The returning hashmap contains (Key, Value) pairs,
     * all keys (K1, K2 ...) are converted to capital letters. errMsg is the
     * customized error message when input csvString does not follow the format.
     *
     * @param csvString
     * @param errMsg
     * @return
     * @throws Exception
     */
    public static HashMap<String, String> getMapFromCSVString(String csvString, String errMsg) throws Exception {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isEmpty(csvString.trim())) {
            return map;
        }
        String[] tuples = csvString.trim().split(",");
        for (String tuple : tuples) {
            String[] onefield = tuple.trim().split(":", 2);
            if (onefield.length != 2) {
                throw new Exception(errMsg);
            } else {
                map.put(onefield[0].trim().toUpperCase(), onefield[1].trim());
            }
        }
        return map;
    }

    /**
     * This method takes in a String which represent an unpadded number, pads it
     * with a single zero and returns padded string. Eg: padNumberWithZero("5")
     * returns "05".
     *
     * @param stringToPadWithZeros
     * @return PaddedString
     * @throws Exception
     */
    public static String padNumberWithZero(String stringToPadWithZeros) throws Exception {
        if (!StringUtils.isInteger(stringToPadWithZeros))
            throw new Exception(" Passed in String is Not a number ");
        if (stringToPadWithZeros.length() < 2)
            stringToPadWithZeros = "0" + stringToPadWithZeros;
        return stringToPadWithZeros;
    }

    /**
     * To generate and return a unique identifier string
     *
     * @param length
     * @return a unique identifier
     */
    public static synchronized String generateUniqueIdentifier() {
        return "UUID-" + UUID.randomUUID().toString();
    }

    /**
     * To generate and return a unique identifier string with specified length. The
     * length must be between 1-36.
     *
     * @param length
     * @return a unique identifier
     * @throws Exception
     */
    public static String generateUniqueIdentifier(int length) throws Exception {
        if (length < 1 || length > 36)
            throw new Exception("The length must be 1-36 (inclusive). You've entered [" + length + "]");
        return UUID.randomUUID().toString().substring(0, length);
    }

    /**
     * To remove the unique identifier from a text, based on the assumption that all
     * unique identifiers have the pattern of "UUID-xxxxxxxxxx "
     *
     * @param text
     * @return text with the unique identifier removed
     */
    public static String removeUUIDfromText(String text) {
        String pattern = "UUID-\\S+\\b";
        return text.replaceAll(pattern, "");
    }

    /**
     * Return the first occurrence of the unique identifier, which start with UUID-
     *
     * @param text
     * @return unique identifier
     */
    public static String getUUIDfromText(String text) {
        String regex = "UUID-\\S+\\b";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find())
            return matcher.group();
        return null;
    }

    /**
     * It uses to add a zero to the number less than 10 or greater than -10.
     *
     * @param strInteger
     * @return
     * @throws Exception
     */
    public static String zeroPad(String strInteger) throws Exception {
        if (StringUtils.isInteger(strInteger))
            if (strInteger.length() == 1)
                strInteger = "0" + strInteger;
            else if (strInteger.contains("-") && strInteger.length() == 2)
                strInteger = "-0" + strInteger.substring(1);
            else
                throw new Exception("The string: " + strInteger + " can't be converted into an integer.");
        return strInteger;
    }

    public static String removeCarriageReturnAndLineFeedFromText(String text) {
        return text.replaceAll("[\\r\\n]", "");
    }

    public static String removeWhitespaces(String text) {
        return text.replaceAll("\\s", "").replace("\u00a0", "");
    }

    public static String generateUniqueNumber(int length) {
        long seconds = System.currentTimeMillis() / 1000l;
        return String.valueOf(seconds).substring(String.valueOf(seconds).length() - length);
    }

    /**
     * convert List Array to Array
     *
     * @param listArray
     * @return
     */
    public static String[] convertArrayListToArray(ArrayList<String> listArray) {
        String[] strArray = (String[]) listArray.toArray(new String[listArray.size()]);
        return strArray;
    }

    /**
     * convert a comma separated string to an array list and trim all elements
     *
     * @param css
     * @return
     */
    public static ArrayList<String> convertCommaSeparatedStringToArrayList(String css) {
        ArrayList<String> al = new ArrayList<String>();
        if (isEmpty(css))
            return al;
        String[] strings = css.split(",");
        for (String str : strings)
            al.add(str.trim());
        return al;
    }

    public static boolean isString(Object o) {
        return o instanceof String;
    }

    /**
     * Compare list of strings
     *
     * @param strList1
     * @param strList2
     * @return true if list matches
     * @throws QualityException with message displaying item in strList1 which does
     *                          not exist in strList2
     */
    public static boolean equalsIgnoreOrder(List<String> strList1, List<String> strList2) throws Exception {
        if (strList1 == null && strList2 == null)
            return true;
        else if (strList1 == null || strList2 == null)
            return false;
        if (strList1.size() != strList2.size())
            return false;
        for (String s1 : strList1)
            if (!StringUtils.isInList(s1, strList2))
                throw new Exception("No match found for: " + s1);
        for (String s2 : strList2)
            if (!StringUtils.isInList(s2, strList1))
                throw new Exception("No match found for: " + s2);
        return true;
    }

    public static boolean isInList(String text, List<String> stringList) {
        for (String s : stringList)
            if (s.equals(text))
                return true;
        return false;
    }

    /**
     * convert a string (separated by a delimiter) into a Set
     *
     * @param string
     * @param delimiter such as ";", "," or ":",...
     * @return
     */
    public static Set<String> convertStringToSet(String string, String delimiter) {
        HashSet<String> set = new HashSet<String>();
        String[] stringAry = string.split(delimiter);
        for (String s : stringAry)
            set.add(s.trim());
        return set;
    }

    /**
     * @param string
     * @return true if s contains a special character
     */
    public static boolean hasSpecialChar(String string) {
        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(string);
        return m.find();
    }

    /**
     * Return matched subset of the full set. Iterate the whole set, find the
     * matched one, then put into the matched subset. Return null, if either fullset
     * or regex is null.
     *
     * @param fullSet
     * @param regex
     * @return
     */
    public static Set<String> findMatches(Set<String> fullSet, String regex) {
        if (fullSet == null || isEmpty(regex))
            return null;
        Set<String> matches = new HashSet<String>();
        if (fullSet.contains(regex))
            matches.add(regex);
        else {
            Iterator<String> it = fullSet.iterator();
            while (it.hasNext()) {
                String str = it.next();
                if (str.matches(regex))
                    matches.add(str);
            }
        }
        return matches;
    }

    /**
     * Enum to use for string generation
     */
    public enum Mode {
        ALPHA, ALPHANUMERIC, NUMERIC
    }

    /**
     * Generate random string of fixed length according "mode" provided
     *
     * @param length
     * @param mode
     * @return
     * @throws Exception
     */
    public static String generateRandomString(int length, Mode mode) throws Exception {
        StringBuffer buffer = new StringBuffer();
        String characters = "";
        switch (mode) {
            case ALPHA:
                characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
                break;
            case ALPHANUMERIC:
                characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
                break;
            case NUMERIC:
                characters = "1234567890";
                break;
        }
        int charactersLength = characters.length();
        for (int i = 0; i < length; i++) {
            double index = Math.random() * charactersLength;
            buffer.append(characters.charAt((int) index));
        }
        return buffer.toString();
    }
}
