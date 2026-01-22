package org.concepts;

//Everything about String data type
public class StringDataType {
    // For a given string, lower case and upper case toggle only Characters of it. For example, from myStRing001 to MYsTrING001
    public static String toggleLowercaseUppercase(String inputString){
        StringBuilder sb = new StringBuilder(inputString);
        for (int i = 0; i < sb.length(); i++) {
            char c = sb.charAt(i);
            if (Character.isLowerCase(c)) {
                sb.setCharAt(i, Character.toUpperCase(c));
            } else if (Character.isUpperCase(c)) {
                sb.setCharAt(i, Character.toLowerCase(c));
            }
        }
        return sb.toString();
    }

    // Calculate how many times a character appears in a string
    public static int countOccurrences(String str, char ch) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ch) {
                count++;
            }
        }
        return count;
    }

    // Reverse a string using StringBuilder with recursion
    public static String reverseString(String str) {
        if (str.length() <= 1) {
            return str;
        } else {
            return reverseString(str.substring(1)) + str.charAt(0);
        }
    }

    static void main() {
        System.out.println(toggleLowercaseUppercase("AFAJLFsgfGASKFADSJL001"));
        System.out.println(countOccurrences("bmvuhfjk#$%%^900jio002", 'b'));
        System.out.println(reverseString("myString001"));

        String s1 = "111";
        String s2 = "222";
        String s3 = "111";
        System.out.println(s1 == s2);
        System.out.println(s1 == s3);
        System.out.println(s1.equals(s3));
        System.out.println(s1.compareTo(s3));
        System.out.println(s1.hashCode());
        s1 = new String("111");
        System.out.println(s1 == s2);
        System.out.println(s1 == s3);
        System.out.println(s1.equals(s3));
        System.out.println(s1.compareTo(s3));
        System.out.println(s1.hashCode());

        //Append 1 to 999 to s1
        s1 = "";
        for (int i = 1; i <= 999; i++) {
            s1 += i;
        }
        System.out.println(s1);

        //Append 1 to 999 to s1 with StringBuilder
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 999; i++) {
            sb.append(i);
        }
        System.out.println(sb.toString());
    }
}
