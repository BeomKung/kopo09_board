package com.example.mytestproject;

import java.util.Arrays;

public class solution2 {
    public int[] solution2(int[][] board, int k) {
        int[] answer = new int[2];
        int n = board.length;
        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, 1, 0, -1};
        int x = 0, y = 0, d = 1, count = 0;

        while (count < k) {
            count++;
            int nx = x + dx[d];
            int ny = y + dy[d];

            if (nx < 0 || nx >= n || ny < 0 || ny >= n || board[nx][ny] == 1) {
                d = (d + 1) % 4;
                continue;
            }

            x = nx;
            y = ny;
        }

        answer[0] = x;
        answer[1] = y;
        return answer;
    }

    public static void main(String[] args) {
        solution2 T = new solution2();
        int[][] arr1 = {
                {0, 0, 0, 0, 0},
                {0, 1, 1, 0, 0},
                {0, 0, 0, 0, 0},
                {1, 0, 1, 0, 1},
                {0, 0, 0, 0, 0}
        };
        int k = 10;

        System.out.println(Arrays.toString(T.solution2(arr1, k)));
    }
}
