import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { People } from './models/people';
import { Place } from './models/place';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ScheduleService {

  constructor(public http: HttpClient) { }

  getSchedules(peoples: Set<People>, places: Set<Place>): Observable<any>{
    return this.http.post("", { peoples: peoples, places: places});
  }
}
