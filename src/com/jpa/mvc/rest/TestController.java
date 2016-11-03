package com.jpa.mvc.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class TestController {
	   @RequestMapping("form")
		public ModelAndView getGoodsForm(String index){
			ModelAndView mv = new ModelAndView();
			mv.setViewName("form");
			return mv;
		}
}
