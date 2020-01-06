import { Component, OnInit } from '@angular/core';
import { People } from '../models/people';
import { Schedule } from '../models/schedule';
import { ScheduleFromPlace } from '../models/schedule-from-place';
import { environment } from '../../environments/environment';

@Component({
  selector: 'app-schedule-table',
  templateUrl: './schedule-table.component.html',
  styleUrls: ['./schedule-table.component.scss']
})
export class ScheduleTableComponent implements OnInit {

  displayedColumns: string[] = ['sun', 'mon', 'tue', 'wed', 'thu', 'fri', 'sat'];
  dataSource = [];
  constructor() {

  }

  ngOnInit() {
    let schedulesFromPlace = environment.EXAMPLE_DATA.map(e => e.schedule);
    let rows = [];
    for (let schedules of schedulesFromPlace) {
      let uniquePeoples: People[] = this.getUniquePeople(schedules.map(e => e.people));
      for (let people of uniquePeoples) {
        let days = schedules.filter(e => e.people.some(e => e.id === people.id)).map(schedule => schedule.days);        
        rows.push(this.populateDay(days, people.name));
      }
    }

    this.dataSource = rows;
  }

  getUniquePeople(peoples2D: People[][]): People[] {
    let peoples1D = [].concat(...peoples2D);
    let uniquePeoples: People[] = [];
    for (let people of peoples1D) {
      if (!uniquePeoples.some(e => e.id == people.id))
        uniquePeoples.push(people);
    }
    return uniquePeoples;
  }

  populateDay(days: string[], peopleName: string) {
    let newRow = {};
    for (let day of days) {
      if (day === "SUN" && !newRow.hasOwnProperty('sun'))
        newRow["sun"] = peopleName;
      else if (day === "MON" && !newRow.hasOwnProperty('mon'))
        newRow["mon"] = peopleName;
      else if (day === "TUE" && !newRow.hasOwnProperty('tue'))
        newRow["tue"] = peopleName;
      else if (day === "WED" && !newRow.hasOwnProperty('wed'))
        newRow["wed"] = peopleName;
      else if (day === "THU" && !newRow.hasOwnProperty('thu'))
        newRow["thu"] = peopleName;
      else if (day === "FRI" && !newRow.hasOwnProperty('fri'))
        newRow["fri"] = peopleName;
      else if (day == "SAT" && !newRow.hasOwnProperty('sat'))
        newRow["sat"] = peopleName;
    }

    return newRow;
  }
}
