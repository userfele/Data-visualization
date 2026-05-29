import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.internal.chartpart.Chart;
import org.knowm.xchart.style.AxesChartStyler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class DataChartFToC {

    static final int WIDTH = 1000;
    static final int HEIGHT = 500;

    public static void main(String[] args) {
        DataChartFToC fToC = new DataChartFToC();
        fToC.drawLineChart();
    }

    public void drawLineChart()  {
        List<Chart> charts = new ArrayList<Chart>();
        XYChart chart = new XYChartBuilder().width(WIDTH).height(HEIGHT).xAxisTitle("X").yAxisTitle("Y").build();
        List<List> allSeries =  getAllSeries("resources/FtoCGraph.csv");
        List<Integer> xData  = allSeries.get(0);
        List<Integer> yData  = allSeries.get(1);
        chart.addSeries("Fahrenheit", xData, yData);
        chart.addSeries("Celsius", yData,  xData);
        chart.setYAxisGroupTitle(0, "Fahrenehit");
        AxesChartStyler styler = (AxesChartStyler) chart.getStyler();
        chart.setXAxisTitle("Celsius");
        charts.add(chart);
        new SwingWrapper(charts).displayChartMatrix();
    }

    public List<List> getAllSeries(String filePath) {
        List<List> allSeries = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            List<Integer> xData = new ArrayList<>();
            List<Integer> yData = new ArrayList<>();
            List<Integer> yData1 = new ArrayList<>();

            String line = reader.readLine(); // skip header
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                xData.add(Integer.parseInt(values[0]));
                yData.add(Integer.parseInt(values[1]));
                if (values.length > 2) {
                    yData1.add(Integer.parseInt(values[2]));
                }
            }
            reader.close();
            //  Add the three series to a List of lists
            allSeries.add(xData);
            allSeries.add(yData);
            // Third column not used
        //  allSeries.add(yData1);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return allSeries;
    }
}
