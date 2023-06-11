package com.course.pmu;

import org.apache.commons.lang3.StringUtils;

public class test {

    private static String motaccent = "Ma mère à été une FEMME FORMADABLE";

    public static void main(String[] args) {
        String mettreEnMinSansAccent = motSansAccentMin(motaccent);
        System.out.println(mettreEnMinSansAccent);
    }

    private static String motSansAccentMin(String motaccent) {
        return StringUtils.stripAccents(motaccent.toLowerCase());
    }
}
