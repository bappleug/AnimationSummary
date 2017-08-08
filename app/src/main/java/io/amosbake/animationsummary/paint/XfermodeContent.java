package io.amosbake.animationsummary.paint;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

/**
 * Created by Ray on 2017/8/8.
 */

public class XfermodeContent {

    String name;
    PorterDuffXfermode xfermode;

    public XfermodeContent(PorterDuff.Mode mode) {
        name = mode.name();
        xfermode = new PorterDuffXfermode(mode);
    }

    public String toString() {
        return name;
    }
}
