import { Schedule } from './schedule';

export interface ScheduleFromPlace {
    placeId: number;
    schedule: Schedule[];
}