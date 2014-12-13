/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author faiez
 */
@Controller
public class HelloController{
   
   @RequestMapping(value = "/", method = RequestMethod.GET)
   public String printHello(ModelMap model) {
      return "hello";
   }
   
   @RequestMapping(value = "/recherche-avancee", method = RequestMethod.GET)
   public String advancedSearch(ModelMap model) {
      return "advancedSearch";
   }

}
