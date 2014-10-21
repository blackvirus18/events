package com.events.service.interfaces;

import java.util.List;

import com.events.model.Club;

public interface IDataExporter {
	public void insertData();
	public List<Club> getData();
}
