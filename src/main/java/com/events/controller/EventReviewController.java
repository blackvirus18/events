package com.events.controller;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.events.model.Club;
import com.events.model.Event;
import com.events.service.interfaces.IDataExporter;
@Controller
@RequestMapping("/reviewEvent")
public class EventReviewController {
	@Resource
    private IDataExporter dataService;
	@RequestMapping(value = "/getevents", method = RequestMethod.GET)
	@ResponseBody
    public List<Club> getRule() {
		System.out.println("code reaches here");
		return dataService.getData();
	}
	@RequestMapping(value = "/approveevents", method = RequestMethod.POST)
	@ResponseBody
    public boolean approveEvents(@RequestBody List<HashMap> events) {
		System.out.println("code reaches here approveEvents"+events);
		dataService.updateApprovedEvents(events);
		return true;
	}
}
