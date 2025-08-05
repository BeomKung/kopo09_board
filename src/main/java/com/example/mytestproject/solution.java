package com.example.mytestproject;

import java.util.Arrays;

public class solution {
    public char [] solution(int n, int[][] ladder){
        char[] answer = new char[n];
        for(int i = 0; i < n; i++) {
            answer[i] = (char)(i + 65);
        }

        for (int[] line : ladder){
            for (int x : line){
                char tmp = answer[x];
                answer[x] = answer[x - 1];
                answer[x - 1] = tmp;
            }
        }
        return  answer;
    }

    public static void main(String[] args){
        solution T = new solution();
        System.out.println(Arrays.toString(T.solution(5, new int[][]{{1, 3}, {2, 4}, {1, 4}})));
    }
}
