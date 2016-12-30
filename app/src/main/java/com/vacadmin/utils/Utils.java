package com.vacadmin.utils;

import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;

/**
 * Created by hemendrag on 12/12/2016.
 */

public class Utils {

    public static final int getColor(Context context, int id) {

        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {

            return ContextCompat.getColor(context, id);

        } else {

            return context.getResources().getColor(id);

        }

    }

}
