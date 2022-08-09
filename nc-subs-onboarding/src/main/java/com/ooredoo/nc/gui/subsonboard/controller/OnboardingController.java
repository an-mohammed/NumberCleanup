package com.ooredoo.nc.gui.subsonboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OnboardingController {

	@RequestMapping("/onboard")
    public String index() {
        return "index.html";
    }
}
