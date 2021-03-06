package com.luv2code.springdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luv2code.springdemo.dao.CustomerDAO;
import com.luv2code.springdemo.entity.Customer;
import com.luv2code.springdemo.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	//need to inject customer DAO into controller
	@Autowired
	private CustomerDAO customerDAO;
	
	// now i'll not use CustomerDAO directly, but use service
	//need to inject customer service into controller
		@Autowired
		private CustomerService customerService;
	
	//@GetMapping and @PostMapping are used for GET and POST request
	//@RequestMapping("/list")
	@GetMapping("/list")
	public String listCustomer(Model model){
		
		//get customer from dao
		//List<Customer> theCustomers = customerDAO.getCustomers();
		
		//now i'll use service	
		List<Customer> theCustomers = customerService.getCustomers();
		
		//add the customer to the model
		
		model.addAttribute("customers",theCustomers);
		
		return "list-customers";
	}

	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model model){
	
		model.addAttribute("customer",new Customer());
		
		return "customer-form";
	}
	
	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute("customer") Customer theCustomer){
	
		//save the customer using our service
		
		customerService.saveCustomer(theCustomer);
		
		return "redirect:/customer/list";
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int theId, Model model){
		
		//get the customer from our service
		Customer theCustomer = customerService.getCustomer(theId);
		
		//set customer as a model attribute to pre-populate the form
		model.addAttribute("customer",theCustomer);
		
		//send over to our form
		return "customer-form";
	}
	
	@GetMapping("/delete")
	public String deleteCustomer(@RequestParam("customerId") int theId){
		
		//delete the customer
		customerService.deleteCustomer(theId);
		
		//send over to our form
		return "redirect:/customer/list";
	}
}
