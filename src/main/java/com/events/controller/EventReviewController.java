package com.events.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping("/reviewEvent")
public class EventReviewController {

	@RequestMapping(value = "/getevents", method = RequestMethod.GET)
	@ResponseBody
    public String getRule() {
		System.out.println("code reaches here");
		return "code working";
	}
}
