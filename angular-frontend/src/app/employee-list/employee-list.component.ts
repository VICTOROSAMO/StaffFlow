import { Component, OnInit } from '@angular/core';
import { Employee } from '../employee';
import { EmployeeService } from '../employee.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-employee-list',
  templateUrl: './employee-list.component.html',
  styleUrls: ['./employee-list.component.css']
})
export class EmployeeListComponent implements OnInit {
  employees: Employee[];

  constructor(private employService: EmployeeService, private router:Router) { 
    this.employees = [];
  }

  ngOnInit(): void {
    this.getEmployees();
  }
   private getEmployees(){
    this.employService.getEmployeeList().subscribe(data => {
      console.log(data);
    this.employees = data;
    })
  }

  updateEmployee(id:number){
    this.router.navigate(['update-employee', id]);
  }

  deleteEmployee(id: number){
    this.employService.deleteEmployee(id).subscribe(data => {
      console.log(data);
      this.getEmployees();
    })
  }

  EmployeeDetails(id: number){
    this.router.navigate(['employee-details', id]);
  }
}
