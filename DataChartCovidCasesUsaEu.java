import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


class DataChartCovidCasesUsaEu {

   public static void main(String[] args) {
       DataChartCovidCasesUsaEu covid = new DataChartCovidCasesUsaEu();
       covid.drawChart();
   }

   public void drawChart() {
       try {
           XYChart chart = new XYChartBuilder().width(800).height(600).title("Date Chart").xAxisTitle("Date").yAxisTitle("Value").build();
           List<List> allSeries = importCSV(chart, new File("resources/daily-cases-covid-19.csv"));
           chart.addSeries("Covid 19 EU Cases", allSeries.get(0), allSeries.get(1));
           chart.addSeries("Covid 19 USA Cases", allSeries.get(0), allSeries.get(2));
           chart.getStyler().setDatePattern("dd-MMM-yy");
           chart.setYAxisTitle("Cases");
           chart.setXAxisTitle("Day-Month-Year");
           chart.setTitle("Covid 19 Cases USA and EU");

           new SwingWrapper<XYChart>(chart).displayChart();
        } catch (IOException | ParseException e) {
             e.printStackTrace();
        }
   }

       public List<List> importCSV(XYChart chart, File file) throws IOException, ParseException {
           // shared dates, two lists for storing data series
           List<List> allSeries = new ArrayList<>();
           List<Date> dates = new ArrayList<>();
           List<Integer> casesUS = new ArrayList<>();
           List<Integer> casesEU = new ArrayList<>();

           try (FileReader reader = new FileReader(file);
                java.io.BufferedReader br = new java.io.BufferedReader(reader)) {

               // Skip the header
               br.readLine();
               // Read and parse the rest of the lines
               String line;
               while ((line = br.readLine()) != null) {
                   String[] parts = line.split(",");
                   String country = parts[0].trim();
                   try {
                       Date date = parseDate(parts[2]);
                       Integer value = Integer.parseInt(parts[3]);
                       if (country.equals("European Union")) {
                           casesEU.add(value);
                           dates.add(date);
                       } else if (country.equals("United States")) {
                           casesUS.add(value);
                      } else {
                           // Ignore other countries
                           continue;
                       }
                   }
                   catch (Exception e) {
                       // Ignore parse format errors in countries other than USA/EU
                       System.out.println(e.getMessage());
                       continue;
                   }
               }
           }
           allSeries.add(dates);
           allSeries.add(casesEU);
           allSeries.add(casesUS);
           return allSeries;
       }

       private Date parseDate(String dateString) throws ParseException {
           // Manually parse the date based on your date format
           return new SimpleDateFormat("dd-MMM-yy").parse(dateString);
       }
   }

