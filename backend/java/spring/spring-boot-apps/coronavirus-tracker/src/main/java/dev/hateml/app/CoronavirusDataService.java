package dev.hateml.app;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoronavirusDataService {

    private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv";

    @Getter
    private static List<CoronavirusDataRow> currentStats = new ArrayList<>();

    @PostConstruct
    @Scheduled(cron = "* 10 * * * *")
    public void fetch() throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(new HttpGet(VIRUS_DATA_URL));
        InputStream content = response.getEntity().getContent();
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(new InputStreamReader(content));

        updateCurrentStats(records);
    }

    private void updateCurrentStats(Iterable<CSVRecord> records) {
        List<CoronavirusDataRow> newStats = new ArrayList<>();
        for (CSVRecord record : records) {
            int latestCases = Integer.parseInt(record.get(record.size() - 1));
            int prevDayCases = Integer.parseInt(record.get(record.size() - 2));

            newStats.add(CoronavirusDataRow.builder()
                    .state(record.get("Province/State"))
                    .country(record.get("Country/Region"))
                    .latestTotalCases(Integer.parseInt(record.get(record.size() - 1)))
                    .diffFromPrevDay(latestCases - prevDayCases)
                    .build());
        }

        currentStats = newStats;
    }

    @Getter
    @Builder
    @RequiredArgsConstructor
    public static class CoronavirusDataRow {

        private final String state;
        private final String country;
        private final int latestTotalCases;
        private final int diffFromPrevDay;

        @Override
        public String toString() {
            return "CoronavirusDataRow{" +
                    "state='" + state + '\'' +
                    ", country='" + country + '\'' +
                    ", latestTotalCases=" + latestTotalCases +
                    ", diffFromPrevDay=" + diffFromPrevDay +
                    '}';
        }
    }
}
