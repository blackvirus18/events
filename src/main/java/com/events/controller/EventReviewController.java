package com.events.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.events.model.Club;
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
}
