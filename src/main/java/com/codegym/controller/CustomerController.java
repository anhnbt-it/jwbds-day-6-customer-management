package com.codegym.controller;

import com.codegym.model.Customer;
import com.codegym.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ModelAndView listCustomers() {
        ModelAndView modelAndView = new ModelAndView("customer/list");
        List<Customer> customers = customerService.findAll();
        modelAndView.addObject("title", "List Customers");
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }

    @GetMapping("create")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("customer/create");
        modelAndView.addObject("title", "Create Customer");
        modelAndView.addObject("customer", new Customer());
        return modelAndView;
    }

    @PostMapping("create")
    public ModelAndView saveCustomer(@ModelAttribute("customer") Customer customer) {
        customerService.save(customer);
        ModelAndView modelAndView = new ModelAndView("customer/create");
        modelAndView.addObject("customer", new Customer());
        modelAndView.addObject("title", "Create Customer");
        modelAndView.addObject("message", "New customer created successfully.");
        return modelAndView;
    }

    @GetMapping("delete/{id}")
    public ModelAndView showDeleteForm(@PathVariable(name = "id") Long id) {
        Customer customer = customerService.findById(id);
        ModelAndView modelAndView = new ModelAndView();
        if (customer != null) {
            modelAndView.setViewName("customer/delete");
            modelAndView.addObject("title", "Delete Customer");
            modelAndView.addObject("customer", customer);
        } else {
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }

    @PostMapping("delete")
    public String deleteCustomer(@ModelAttribute("customer") Customer customer, RedirectAttributes redirectAttributes) {
        customerService.remove(customer.getId());
        redirectAttributes.addFlashAttribute("message", "Was successfully deleted.");
        return "redirect:/customers";
    }

    @GetMapping("edit/{id}")
    public ModelAndView showEditform(@PathVariable("id") Long id) {
        Customer customer = customerService.findById(id);
        ModelAndView modelAndView = new ModelAndView();
        if (customer != null) {
            modelAndView.setViewName("customer/edit");
            modelAndView.addObject("title", "Edit Customer");
            modelAndView.addObject("customer", customer);
        } else {
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }

    @PostMapping("edit")
    public ModelAndView editCustomer(@ModelAttribute("customer") Customer customer) {
        customerService.save(customer);
        ModelAndView modelAndView = new ModelAndView("customer/edit");
        modelAndView.addObject("title", "Edit Customer");
        modelAndView.addObject("message", "Updated record successfully.");
        modelAndView.addObject("customer", customer);
        return modelAndView;
    }
}
