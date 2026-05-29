import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateChartWorldPopulation {

    public static void main(String[] args) {
        DateChartWorldPopulation worldPop = new DateChartWorldPopulation();
        worldPop.drawChart();
    }

    public void drawChart() {
        XYChart chart = new XYChartBuilder()
                .width(800)
                .height(600)
                .title("World Population 1950-2020")
                .xAxisTitle("Year")
                .yAxisTitle("World Population (Millions)")
                .build();

        try {
            List<List> allSeries = getAllSeries(new File("resources/WorldPopulation.csv"));
            chart.addSeries("World Population", allSeries.get(0), allSeries.get(1));

            chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
            chart.getStyler().setXAxisTickMarkSpacingHint(100);
            chart.getStyler().setDatePattern("yyyy");

            new SwingWrapper<>(chart).displayChart();

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private Date parseDate(String dateString) throws ParseException {
        return new SimpleDateFormat("yyyy").parse(dateString);
    }

    public List<List> getAllSeries(File file) throws IOException, ParseException {
        List<List> allSeries = new ArrayList<>();
        List<Date> dates = new ArrayList<>();
        List<Integer> values = new ArrayList<>();

        FileReader reader = new FileReader(file);
        BufferedReader br = new BufferedReader(reader);

        br.readLine();

        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");

            Date date = parseDate(parts[0].trim());
            Integer population = Integer.parseInt(parts[1].replaceAll(" ", "").trim());

            dates.add(date);
            values.add(population);
        }

        br.close();
        reader.close();

        allSeries.add(dates);
        allSeries.add(values);

        return allSeries;
    }
}
