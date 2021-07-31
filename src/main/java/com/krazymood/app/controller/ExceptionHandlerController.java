package com.krazymood.app.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {
   @ExceptionHandler(value = RuntimeException.class)
   public String exception(Model model) {
	  model.addAttribute("lan","hindi");
      return "404.html";
   }
}