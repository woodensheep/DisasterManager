
package com.nandi.disastermanager.search;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.nandi.disastermanager.R;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Custom implementation of the MarkerView.
 * 
 * @author Philipp Jahoda
 */
public class MyMarkerView extends MarkerView {

    private TextView tvContent;
    private List<List<Double>> lists;
    public MyMarkerView(Context context, int layoutResource,List<List<Double>> lists) {
        super(context, layoutResource);

        tvContent = (TextView) findViewById(R.id.tvContent);
        this.lists=lists;
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (users-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        if (e instanceof CandleEntry) {

            CandleEntry ce = (CandleEntry) e;

            tvContent.setText("" + Utils.formatNumber(ce.getHigh(), 0, true));
        } else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            double time=lists.get((int)e.getX()).get(0);
            date.setTime(Long.parseLong(new BigDecimal(time).toPlainString()));
            String s = simpleDateFormat.format(date);
//            tvContent.setText("" + Utils.formatNumber(e.getY(), 0, true));
            tvContent.setText(s+"，数据值："+e.getY());

        }

        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
