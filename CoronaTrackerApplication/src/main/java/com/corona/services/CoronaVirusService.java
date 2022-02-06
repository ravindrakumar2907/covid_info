package com.corona.services;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import models.LocationStats;

@Service
public class CoronaVirusService {

	//https://commons.apache.org/proper/commons-csv/
	//https://github.com/CSSEGISandData/COVID-19
	final private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_daily_reports_us/01-01-2021.csv";
	private List<LocationStats> listMainLocationStats = new ArrayList<>();
	
	public List<LocationStats> getListMainLocationStats() {
		return listMainLocationStats;
	}

	public void setListMainLocationStats(List<LocationStats> listMainLocationStats) {
		this.listMainLocationStats = listMainLocationStats;
	}

	@PostConstruct
	//@Scheduled(cron = "second minute hour day year")
	@Scheduled(cron = "* 1 * * * *")
	public void fetchVirusData() {
		List<LocationStats> listLocationStats = new ArrayList<>();
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request= HttpRequest.newBuilder().uri(URI.create(VIRUS_DATA_URL)).build();
		
		try {
			HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
			System.out.println(httpResponse.toString());
			//System.out.println(httpResponse.body());
			StringReader reader = new StringReader(httpResponse.body());
			Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
			for (CSVRecord record : records) {
				LocationStats state = new LocationStats();
			    String Province_State = record.get("Province_State");
			    String Country_Region = record.get("Country_Region");
			    String Last_Update = record.get("Last_Update");
			    String Lat = record.get("Lat");
			    String Long_ = record.get("Long_");
			    String Confirmed = record.get("Confirmed");
			    String Deaths = record.get("Deaths");
			    String Active = record.get("Active");
			    String Recovered = record.get("Recovered");
			    String FIPS = record.get("FIPS");
			    String Incident_Rate = record.get("Incident_Rate");
			    System.out.println(Province_State);
			    state.setState(Province_State);
			    int totalCases = Integer.parseInt(Confirmed)-1;
			    state.setLatesTotalCases(totalCases);
			    int diffPreDayCases = totalCases -2 ;
			    state.setDiffFromPrevDay(totalCases - diffPreDayCases);
			    state.setCountry(Country_Region);
			    listLocationStats.add(state);
			}
			this.listMainLocationStats = listLocationStats;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
