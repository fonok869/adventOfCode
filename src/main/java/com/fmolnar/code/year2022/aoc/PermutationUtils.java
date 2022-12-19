package com.fmolnar.code.year2022.aoc;

public class PermutationUtils {

    public static void permuter(char [] t, int inf, int sup) {
        char s[] = (char []) t.clone();
        if (inf < sup)
            for (int i = inf; i <= sup; i++) {
                echanger(s, inf, i);
                permuter(s, inf+1, sup);
            }
        else // écrire sur la sortie standard une permutation trouvée
            afficherPermutation(s, 0, t.length - 1 );
    }
    private static void echanger(char [] t, int i, int j) {
        char x = t[i];
        t[i] = t[j];
        t[j] = x;
    }
    private static void afficherPermutation(char [] t, int inf, int sup) {
//        for (int i=inf; i<=sup; i++) System.out.print(t[i]);
//        System.out.println();
    }
    public static void main(String [] arg) {
        char [] t = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'n'};
        long begin = System.currentTimeMillis();
        permuter(t, 0, t.length -1);
        System.out.println("End: " + (System.currentTimeMillis() - begin)/(1000l * 60l) + "m");

    }
}
