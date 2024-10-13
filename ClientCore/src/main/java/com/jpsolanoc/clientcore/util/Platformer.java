package com.jpsolanoc.clientcore.util;

import java.util.ArrayList;

public class Platformer {

    private int position;
    static ArrayList<Integer> row;

    public Platformer(int n, int position) {
        row = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            row.add(i);
        }
        this.position = row.get(position+1);
    }

    public void jumpLeft() {
        System.out.println(row.toString());
        int mov = position - 2;
        position =row.indexOf(mov);
        row.remove(position);
    }

    public void jumpRight() {
        int mov = position + 2;
        position = row.get(mov);
        row.remove(position);
    }

    public int position() {
        return this.position;
    }

    public static void main(String[] args) {
        Platformer platformer = new Platformer(6, 3);
        System.out.print(platformer.position()); // should print 3
        System.out.println(row.toString());

        platformer.jumpLeft();
        System.out.print(platformer.position()); // should print 1
        System.out.println(row.toString());

        platformer.jumpRight();
        System.out.print(platformer.position()); // should print 4
        System.out.println(row.toString());
    }
}
