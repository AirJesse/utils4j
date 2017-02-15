package com.lujiachao.support.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Random;

/**
 * Created by cyril on 17-2-15.
 */
public class LjcRandomUtils {

    public static double[] randomArray(int size, double basis) {
        if (size <= 0) {
            return new double[0];
        } else {
            Random generator = new Random();
            double[] fv = new double[size];
            for (int i = 0; i < size; ++i) {
                fv[i] = basis + generator.nextDouble();
            }
            return fv;
        }
    }

    public static long[] randomArray(int size, long basis) {
        if (size <= 0) {
            return new long[0];
        } else {
            Random generator = new Random();
            long[] fv = new long[size];
            for (int i = 0; i < size; ++i) {
                fv[i] = basis + generator.nextLong();
            }
            return fv;
        }
    }

    public static int[] randomArray(int size, int basis) {
        if (size <= 0) {
            return new int[0];
        } else {
            Random generator = new Random();
            int[] fv = new int[size];
            for (int i = 0; i < size; ++i) {
                fv[i] = basis + generator.nextInt();
            }
            return fv;
        }
    }

    public static float[] randomArray(int size, float basis) {
        if (size <= 0) {
            return new float[0];
        } else {
            Random generator = new Random();
            float[] fv = new float[size];
            for (int i = 0; i < size; ++i) {
                fv[i] = basis + generator.nextFloat();
            }
            return fv;
        }
    }

    public static Date randomDate(Date start, Date end) {
        if (start.before(end)) {
            long timeRange = (end.getTime() - start.getTime());
            return new Date(start.getTime() + Double.valueOf((timeRange * Math.random())).longValue());
        } else {
            return randomDate(end, start);
        }
    }

    public static Date randomDate(Date start, long timeRange) {
        return new Date(start.getTime() + Double.valueOf((timeRange * Math.random())).longValue());
    }

}
