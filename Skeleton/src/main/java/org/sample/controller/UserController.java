/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sample.controller;

import javax.validation.Valid;

import org.sample.controller.pojos.SignupUser;
import org.sample.controller.service.SampleService;
import org.sample.exceptions.InvalidUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {
    
    @Autowired
    SampleService sampleService;

    
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView showProfile(@RequestParam(value = "id", required = false) long id) {
     ModelAndView model = new ModelAndView("profile");
     model.addObject("currentUser", sampleService.getUser(id));
     return model;
    }

    
}
