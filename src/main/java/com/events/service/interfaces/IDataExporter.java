package com.events.service.interfaces;

import java.util.HashMap;
import java.util.List;

import com.events.model.Club;
import com.events.model.Event;

public interface IDataExporter {
	public void insertData();
	public List<Club> getData();
	public void updateApprovedEvents(List<HashMap> events);
}
