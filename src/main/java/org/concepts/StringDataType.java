package org.concepts;

//Everything about String data type
public class StringDataType {
    static void main() {
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
