package com.krazymood.app.component;

public class pakbudhwak {
    public static void main(String[] args) {
        String str="*यूँ  तो <span style=\"color: #e03e2d; font-size: 14pt;\">\"शब्द\" \uD83D\uDCD6 मुफ्त में मिलते ";
        System.out.println(str.replaceAll("\\<.*?\\>", ""));
    }
}
