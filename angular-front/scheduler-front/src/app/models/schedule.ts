import { People } from './people';

export interface Schedule {
    days: string;
    shift: string;
    people: People[];
}