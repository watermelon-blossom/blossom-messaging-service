package com.watermelon.chat.application;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TestController {

	@GetMapping("/test1")
	public String socketTest() {
		return "test1.html";
	}

	@GetMapping("/test2")
	public String readRoomTest() {
		return "test2.html";
	}
}
