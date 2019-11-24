package com.modelplayground.server.utils;

import static java.lang.Math.ceil;

public class StringUtils {

    public static String getLexographicMiddleString(String s1,String s2) {
        StringBuilder res = new StringBuilder();
        if(s1.compareTo(s2)>0) return null;
        while (s1.length() < s2.length()) {
            s1 += 'a';
        }
        while (s2.length() < s1.length()) {
            s2 += 'a';
        }
        boolean consChars = false;
        int i=0;
        while(i<s1.length()){
            if( (char)('a'+(s1.charAt(i)+1-'a')%26) == s2.charAt(i) ){
                res.append(s1.charAt(i));
                consChars = true;
            }else if(consChars){
                int count = (int)ceil((double)('z'-s1.charAt(i)+s2.charAt(i)-'a')/2);
                int charCount = (s1.charAt(i) - 'a') + count;
                if(charCount>=26){
                    res.setCharAt(i-1,(char)(res.charAt(i-1)+1));
                }
                res.append((char)('a'+charCount%26));
                return res.toString();
            }else if(s1.charAt(i)==s2.charAt(i)){
                res.append(s1.charAt(i));
            }else{
                if(s1.charAt(i) < s2.charAt(i)){
                    int count = (int)ceil((double) (s2.charAt(i)-s1.charAt(i)-1)/2);
                    res.append((char)(s1.charAt(i)+count));
                    return res.toString();
                }else{
                    return null;
                }
            }
            ++i;
        }
        res.append('m');
        return res.toString();
    }


}
