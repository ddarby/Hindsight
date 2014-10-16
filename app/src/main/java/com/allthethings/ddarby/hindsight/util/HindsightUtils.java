package com.allthethings.ddarby.hindsight.util;

import com.allthethings.ddarby.hindsight.model.Task;

import java.util.List;

/**
 * Created by ddarby on 10/15/14.
 */
public class HindsightUtils {

    public static String joinTasks(CharSequence delimiter, int[] ids) {
        StringBuilder sb = new StringBuilder();
        boolean firstTime = true;
        for (int i=0; i<ids.length; i++) {
            if (firstTime) {
                firstTime = false;
            } else {
                sb.append(delimiter);
            }
            sb.append(ids[i]);
        }
        return sb.toString();
    }

    public static int[] getTaskIds(List<Task> tasks) {
        int[] returnIds = new int[tasks.size()];
        for(int i=0; i<tasks.size(); i++){
            returnIds[i] = tasks.get(i).getId();
        }
        return returnIds;
    }



}
