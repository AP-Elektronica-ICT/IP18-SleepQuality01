package be.eaict.sleepqualitymeter;

import android.graphics.Color;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.LineDataSet;

import static android.graphics.Color.rgb;

public class CharStyler {

    int accentcolor = rgb(186, 80, 5);
    int textcolor = rgb(224, 224, 224);
    int background = rgb(100,100,100);

    public void SetChartColor(LineDataSet lineDataSet){
        lineDataSet.setColor(accentcolor);
        lineDataSet.setCircleColor(accentcolor);
        lineDataSet.setDrawCircles(false);
    }

    public void SetBarChartColor(BarDataSet barDataSet){
        barDataSet.setColor(accentcolor);
    }

    public void SetChartLegendColor(LineChart lineChart){
        lineChart.getXAxis().setTextColor(textcolor);
        lineChart.getLegend().setTextColor(textcolor);
        lineChart.getAxisLeft().setTextColor(textcolor);
        lineChart.getAxisRight().setTextColor(textcolor);
        lineChart.setDrawGridBackground(true);
        lineChart.setGridBackgroundColor(background);
        lineChart.getDescription().setEnabled(false);
    }

    public void SetBarChartLegendColor(BarChart barChart){
        barChart.getXAxis().setTextColor(textcolor);
        barChart.getLegend().setTextColor(textcolor);
        barChart.getAxisLeft().setTextColor(textcolor);
        barChart.getAxisRight().setTextColor(textcolor);
        barChart.setDrawGridBackground(true);
        barChart.setGridBackgroundColor(background);
        barChart.getDescription().setEnabled(false);
    }
}
