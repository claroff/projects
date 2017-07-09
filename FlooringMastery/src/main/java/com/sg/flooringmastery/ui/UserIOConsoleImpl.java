/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.ui;

import java.util.Scanner;

/**
 *
 * @author chandler
 */
public class UserIOConsoleImpl implements UserIO {

    Scanner sc = new Scanner(System.in);

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public double readDouble(String prompt) {
        while (true) {
            try {
                System.out.println(prompt);
                return Double.parseDouble(sc.nextLine());
            } catch (NumberFormatException e) {
            }
            System.out.println("Please enter a valid number.");
        }
    }

    @Override
    public double readDouble(String prompt, double min, double max) {
        double temp = 0;
        do {
            System.out.println(prompt);
            temp = Double.parseDouble(sc.nextLine());
        } while (temp < min || temp > max);

        return temp;
    }

    @Override
    public float readFloat(String prompt) {
        while (true) {
            try {
                System.out.println(prompt);
                return Float.parseFloat(sc.nextLine());
            } catch (NumberFormatException e) {
            }
            System.out.println("Please enter a valid number.");
        }
    }

    @Override
    public float readFloat(String prompt, float min, float max) {
        float temp = 0;
        while (true) {
            try {
                do {
                    System.out.println(prompt);
                    temp = Float.parseFloat(sc.nextLine());
                } while (temp < min || temp > max);
                return temp;
            } catch (NumberFormatException e) {
            }
            System.out.println("Please enter valid number.");
        }
    }

    @Override
    public int readInt(String prompt) {
        while (true) {
            try {
                System.out.println(prompt);
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
            }
            System.out.println("Please enter a valid number.");
        }
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        int temp = 0;
        while (true) {
            try {
                do {
                    System.out.println(prompt);
                    temp = Integer.parseInt(sc.nextLine());
                } while (temp < min || temp > max);
                return temp;
            } catch (NumberFormatException e) {
            }
            System.out.println("Please enter valid number.");
        }
    }

    @Override
    public int readIntEmptyString(String prompt, int min, int max) {
        int temp = -1;
        while (true) {
            try {
                do {
                    System.out.println(prompt);
                    String nextLine = sc.nextLine();
                    if (nextLine.equals("")) {
                        return temp = 0;
                    }
                    temp = Integer.parseInt((nextLine));
                } while (temp < min || temp > max);
                return temp;
            } catch (NumberFormatException e) {
            }
            System.out.println("Please enter valid number.");
        }
    }

    @Override
    public long readLong(String prompt) {
        while (true) {
            try {
                System.out.println(prompt);
                return Long.parseLong(sc.nextLine());
            } catch (NumberFormatException e) {
            }
            System.out.println("Please enter a valid number.");
        }
    }

    @Override
    public long readLong(String prompt, long min, long max) {
        long temp = 0;
        while (true) {
            try {
                do {
                    System.out.println(prompt);
                    temp = Long.parseLong(sc.nextLine());
                } while (temp < min || temp > max);
                return temp;
            } catch (NumberFormatException e) {
            }
            System.out.println("Please enter valid number.");
        }
    }

    @Override
    public String readString(String prompt) {
        System.out.println(prompt);
        return sc.nextLine();
    }

}
