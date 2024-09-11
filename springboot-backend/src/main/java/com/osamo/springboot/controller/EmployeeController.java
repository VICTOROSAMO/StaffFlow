package com.osamo.springboot.controller;

import com.osamo.springboot.exception.ResourceNotFoundException;
import com.osamo.springboot.model.Employee;
import com.osamo.springboot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
//@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

 // Handling preflight requests
 @RequestMapping(value = "/**", method = RequestMethod.OPTIONS)
 public ResponseEntity<?> handleOptions() {
  return ResponseEntity.ok().build();
 }

    //get all employees rest api
   @GetMapping("/employees")
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    // create employees rest api
    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee){
    return employeeRepository.save(employee);
    }

    //get employee by id rest api
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployById(@PathVariable Long id){
    Employee employee = employeeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Employ not exist with id" + id));
    return ResponseEntity.ok(employee);
    }

    // update employee rest api
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable ("id") Long id, @RequestBody Employee employeeDetails){
    Employee employee = employeeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Employ not exist with id" + id));

     employee.setFirstName(employeeDetails.getFirstName());
     employee.setLastName(employeeDetails.getLastName());
     employee.setEmailId(employeeDetails.getEmailId());

     Employee updatedEmployee = employeeRepository.save(employee);
     return ResponseEntity.ok(updatedEmployee);

    }

    // delete employee rest api
 @DeleteMapping("/employees/{id}")

  public ResponseEntity <Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
   Employee employee = employeeRepository.findById(id)
           .orElseThrow(() -> new ResourceNotFoundException("Employ not exist with id" + id));
    employeeRepository.delete(employee);
    Map<String, Boolean> response = new HashMap<>();
    response.put("Deleted", Boolean.TRUE);
    return ResponseEntity.ok(response);



  }
}

