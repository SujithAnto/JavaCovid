package com.example.CoronoVirusTracker.services;

import com.example.CoronoVirusTracker.Models.LocationStats;
import jakarta.annotation.PostConstruct;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoronoVirusDataService {
    private static String Data_url="https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    private List<LocationStats> allStats=new ArrayList<>();

    public List<LocationStats> getAllStats() {
        return allStats;
    }

    @PostConstruct
    @Scheduled(cron="* * 1 * * *")
    public void FetchDataSet() throws IOException, InterruptedException {
        List<LocationStats> newStats=new ArrayList<>();
        HttpClient client= HttpClient.newHttpClient();
       HttpRequest request= HttpRequest.newBuilder()
                .uri(URI.create(Data_url))
               .build();
      HttpResponse<String>httpResponse= client.send(request, HttpResponse.BodyHandlers.ofString());
        StringReader csvBodyReader=new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        for (CSVRecord record : records) {
           LocationStats locationStats=new LocationStats();
            locationStats.setState(record.get("Province/State"));
           locationStats.setCountry(record.get("Country/Region"));
         int latestCases=Integer.parseInt(record.get(record.size()-1));
            int prevDayCase=Integer.parseInt(record.get(record.size()-2));
           locationStats.setLatestTotalCases(Integer.parseInt(record.get(record.size()-1)));
          locationStats.setDiffFromPrevDay(latestCases-prevDayCase);
            newStats.add(locationStats);
        }
        this.allStats=newStats;
    }
}
