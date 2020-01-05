import { Component, OnInit } from '@angular/core';
import { stringify } from 'querystring';

export interface People {
  id: number;
  name: string;
}

export interface Schedule {
  days: string;
  shift: string;
  people: People[];
}

export interface ScheduleFromPlace {
  placeId: number;
  schedule: Schedule[];
}

const EXAMPLE_DATA: ScheduleFromPlace[] = [
  {
    placeId: 1,
    schedule: [
      {
        days: "SUN",
        shift: "Morning",
        people: []
      },
      {
        days: "SUN",
        shift: "Night",
        people: [
          {
            id: 1,
            name: "Steve"
          }
        ]
      },
      {
        days: "SUN",
        shift: "Afternoon",
        people: [
          {
            id: 1,
            name: "Steve"
          }
        ]
      },
      {
        days: "MON",
        shift: "Morning",
        people: [
          {
            id: 1,
            name: "Steve"
          }
        ]
      },
      {
        days: "MON",
        shift: "Night",
        people: [
          {
            id: 1,
            name: "Steve"
          }
        ]
      },
      {
        days: "MON",
        shift: "Afternoon",
        people: [
          {
            id: 1,
            name: "Steve"
          }
        ]
      },
      {
        days: "TUE",
        shift: "Morning",
        people: [
          {
            id: 1,
            name: "Steve"
          }
        ]
      },
      {
        days: "TUE",
        shift: "Night",
        people: [
          {
            id: 1,
            name: "Steve"
          }
        ]
      },
      {
        days: "TUE",
        shift: "Afternoon",
        people: [
          {
            id: 1,
            name: "Steve"
          }
        ]
      },
      {
        days: "WED",
        shift: "Morning",
        people: [
          {
            id: 1,
            name: "Steve"
          }
        ]
      },
      {
        days: "WED",
        shift: "Night",
        people: [
          {
            id: 1,
            name: "Steve"
          }
        ]
      },
      {
        days: "WED",
        shift: "Afternoon",
        people: [
          {
            id: 1,
            name: "Steve"
          }
        ]
      },
      {
        days: "THU",
        shift: "Morning",
        people: [
          {
            id: 1,
            name: "Steve"
          }
        ]
      },
      {
        days: "THU",
        shift: "Night",
        people: [
          {
            id: 1,
            name: "Steve"
          }
        ]
      },
      {
        days: "THU",
        shift: "Afternoon",
        people: [
          {
            id: 1,
            name: "Steve"
          }
        ]
      },
      {
        days: "FRI",
        shift: "Morning",
        people: [
          {
            id: 1,
            name: "Steve"
          }
        ]
      },
      {
        days: "FRI",
        shift: "Night",
        people: [
          {
            id: 1,
            name: "Steve"
          }
        ]
      },
      {
        days: "FRI",
        shift: "Afternoon",
        people: [
          {
            id: 1,
            name: "Steve"
          }
        ]
      },
      {
        days: "SAT",
        shift: "Morning",
        people: [
          {
            id: 1,
            name: "Steve"
          }
        ]
      },
      {
        days: "SAT",
        shift: "Night",
        people: [
          {
            id: 1,
            name: "Steve"
          }
        ]
      },
      {
        days: "SAT",
        shift: "Afternoon",
        people: [
          {
            id: 1,
            name: "Steve"
          }
        ]
      },
    ]
  }
];

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
    let schedulesFromPlace = EXAMPLE_DATA.map(e => e.schedule);
    let rows = [];    
    for (let schedules of schedulesFromPlace) {      
      let peoples2D = schedules.map(e => e.people);
      let peoples1D = [].concat(...peoples2D);
      let uniquePeoples: People[] = [];
      for (let people of peoples1D) {
        if (!uniquePeoples.some(e => e.id == people.id))
          uniquePeoples.push(people);
      }
      debugger;
      for (let people of uniquePeoples) {
        let days = schedules.filter(e => e.people.some(e => e.id === people.id)).map(schedule => schedule.days);
        let newRow = {};
        for (let day of days) {

          if (day === "SUN")
            newRow["sun"] = people.name;
          else if (day === "MON")
            newRow["mon"] = people.name;
          else if (day === "TUE")
            newRow["tue"] = people.name;
          else if (day === "WED")
            newRow["wed"] = people.name;
          else if (day === "THU")
            newRow["thu"] = people.name;
          else if (day === "FRI")
            newRow["fri"] = people.name;
          else
            newRow["sat"] = people.name;
        }
        rows.push(newRow);
      }
    }

    this.dataSource = rows;
    console.log(this.dataSource);
  }

}
