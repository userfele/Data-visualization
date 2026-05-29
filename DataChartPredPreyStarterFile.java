/**
 * Copyright 2015-2017 Knowm Inc. (http://knowm.org) and contributors.
 * Copyright 2011-2015 Xeiam LLC (http://xeiam.com) and contributors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
//package org.knowm.xchart.standalone.issues;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.internal.chartpart.Chart;
import org.knowm.xchart.internal.series.Series;
import org.knowm.xchart.style.AxesChartStyler;
import org.knowm.xchart.style.Styler.YAxisPosition;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Create a Chart matrix
 *
 * @author timmolter
 */
public class DataChartPredPreyStarterFile {

    static final int WIDTH = 1000;
    static final int HEIGHT = 500;

    public static void main(String[] args) {
        DataChartPredPreyStarterFile predPreyChart = new DataChartPredPreyStarterFile();
        predPreyChart.drawChart();
    }

   public void drawChart() {
        List<Chart> charts = new ArrayList<Chart>();
        XYChart chart = new XYChartBuilder().width(WIDTH).height(HEIGHT).xAxisTitle("X").yAxisTitle("Y").build();
        List<List> allSeries = getAllSeries("resources/predprey.csv");
        List<Integer> xData = allSeries.get(0);
        List<Integer> yData = allSeries.get(1);
        List<Integer> yData2 = allSeries.get(2);
        AxesChartStyler styler = (AxesChartStyler) chart.getStyler();
        styler.setXAxisMin(0.0);
        if (xData.size() > 0 && yData.size() > 0 && yData2.size() > 0 ) {
            styler.setXAxisMax(((double) xData.get(xData.size() - 1) + 10));
            styler.setYAxisMax(0, (double) Collections.max(yData2) + 5.0);
            styler.setYAxisMin(0, (double) Collections.min(yData2));
            chart.addSeries("Prey", xData, yData);
            chart.addSeries("Predator", xData, yData2);
            chart.setTitle("Predator Prey Populations");
            Series series = (Series) chart.getSeriesMap().get("Prey");
            series.setYAxisGroup(1);
            chart.setYAxisGroupTitle(1, "Prey");
            chart.setYAxisGroupTitle(0, "Predator");
            chart.setXAxisTitle("Generation");
            chart.getStyler().setYAxisGroupPosition(1, YAxisPosition.Right);
            charts.add(chart);
            new SwingWrapper(charts).displayChartMatrix();
        }
        else  {
            System.out.println("Error: Data series not populated.");
        }
    }

    public List<List> getAllSeries(String filePath) {

        List<List> allSeries = new ArrayList<>();
        List<Integer> xData = new ArrayList<>();
        List<Integer> yData = new ArrayList<>();
        List<Integer> yData1 = new ArrayList<>();
        allSeries.add(xData);
        allSeries.add(yData);
        allSeries.add(yData1);

        try {
                BufferedReader reader = new BufferedReader(new FileReader(filePath));
                String line = reader.readLine();

                while ((line = reader.readLine()) != null) {
                    String[] values = line.split(",");

                    int period = Integer.parseInt(values[0].trim());
                    int prey = Integer.parseInt(values[1].trim());
                    int predator = Integer.parseInt(values[2].trim());

                    xData.add(period);
                    yData.add(prey);
                    yData1.add(predator);
                }

                reader.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return allSeries;
    }

}