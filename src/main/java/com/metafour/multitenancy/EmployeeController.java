package com.metafour.multitenancy;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.metafour.multitenancy.bean.Employee;
import com.metafour.multitenancy.bean.EmployeeRepository;

/**
 * Spring controller to handle employee list, add calls.
 * 
 * @author Imtiaz Rahi
 * @since 2017-08-25
 */
@Controller
@RequestMapping("/{tenantid}")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@RequestMapping
	// public String employees(@RequestHeader("X-TenantID") String tenantid, Model model) {
	// TenantContext.setCurrentTenant(tenantid);
	public String employees(@PathVariable String tenantid, Model model) {
		model.addAttribute("tenantid", tenantid);
		model.addAttribute("employee", new Employee());
		model.addAttribute("employees", employeeRepository.findAll());
		return "employees";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@Transactional
	// public String addEmployee(@RequestHeader("X-TenantID") String tenantid, @ModelAttribute Employee employee, Model
	// model) {
	public String addEmployee(@ModelAttribute Employee employee, Model model) {
		employeeRepository.save(employee);
		return "redirect:/{tenantid}";
	}
}
