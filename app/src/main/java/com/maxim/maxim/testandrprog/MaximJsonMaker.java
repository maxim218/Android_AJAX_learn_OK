package com.maxim.maxim.testandrprog;


public class MaximJsonMaker {
    private String [] m1 = new String[15];
    private String [] m2 = new String[15];
    private int count = 0;

    public void addElement(String s1, String s2) {
        m1[count] = s1;
        m2[count] = s2;
        count++;
    }

    public String getMaximJsonObj() {
        String answer = "";
        for(int i = 0; i < count; i++) {
            answer = answer + '"' + m1[i] + '"' + ':' + '"' + m2[i] + '"';
            if(i != count - 1) {
                answer += ",";
            }
        }
        answer = "{" + answer + "}";
        return answer;
    }
}
