package org.concepts;

import java.util.HashMap;
import java.util.Map;

// Examples for Java String operations.
public class StringDataType {
    // Toggles letter case while leaving digits/symbols unchanged (e.g., myStRing001 -> MYsTrING001).
    public static String toggleLowercaseUppercase(String inputString) {
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

    // Finds the length of the longest substring without repeating characters.
    public static int lengthOfLongestSubstring(String s) {
        int maxLength = 0;
        for (int i = 0; i < s.length(); i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = i; j < s.length(); j++) {
                if (sb.indexOf(String.valueOf(s.charAt(j))) != -1) {
                    break;
                }
                sb.append(s.charAt(j));
                maxLength = Math.max(maxLength, sb.length());
            }
        }
        return maxLength;
    }

    public static int lengthOfLongestSubstringFastSolution(String s) {
        int maxLength = 0;
        Map<Character, Integer> charIndexMap = new HashMap<>();
        int left = 0;
        for (int right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);
            if (charIndexMap.containsKey(currentChar) && charIndexMap.get(currentChar) >= left) {
                left = charIndexMap.get(currentChar) + 1;
            }
            charIndexMap.put(currentChar, right);
            maxLength = Math.max(maxLength, right - left + 1);
        }
        return maxLength;
    }

    public static int lengthOfLongestSubstringFasterSolution(String s) {
        int maxLength = 0;
        for (int right = 0, left = 0; right < s.length(); right++) {
            int indexOfFirstAppearanceInSubstring = s.indexOf(s.charAt(right), left);
            if (indexOfFirstAppearanceInSubstring != right) {
                left = indexOfFirstAppearanceInSubstring + 1;
            }
            maxLength = Math.max(maxLength, right - left + 1);
        }
        return maxLength;
    }

    static void main() {
        System.out.println(lengthOfLongestSubstring("abcabcadefgbb"));
        System.out.println(lengthOfLongestSubstringFastSolution("abcabcadefgbb"));
        System.out.println(lengthOfLongestSubstringFasterSolution("abcabcadefgbb"));
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

        // Append 1 to 999 to s1.
        s1 = "";
        for (int i = 1; i <= 999; i++) {
            s1 += i;
        }
        System.out.println(s1);

        // Append 1 to 999 to s1 with StringBuilder.
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 999; i++) {
            sb.append(i);
        }
        System.out.println(sb.toString());
    }
}
