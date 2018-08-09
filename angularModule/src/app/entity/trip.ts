import {Departure} from './departure';

export class Trip {
  public chosen: number[];

  constructor(public id: any, public departures: Departure[], public coachCount: number) {
    this.chosen = [];
  }

  static build(obj: any) {
    return new Trip(obj.id, obj.departures, obj.coachCount);
  }

  public removeChosen(ind: number): boolean {
    const indexInChosen: number = this.chosen.indexOf(ind);
    if (indexInChosen === -1) {
      return false;
    } else {
      this.chosen.splice(indexInChosen, 1);
      return true;
    }
  }

  public addChosen(ind: number): boolean {
    const indexInChosen: number = this.chosen.indexOf(ind);
    if (indexInChosen === -1) {
      this.chosen.push(ind);
      this.chosen.sort();
      return true;
    } else {
      return false;
    }
  }

  public toggleChosen(ind: number): void {
    const indexInChosen: number = this.chosen.indexOf(ind);
    if (indexInChosen !== -1) {
      this.chosen.splice(indexInChosen, 1);
    } else {
      if (this.chosen.length < 2) {
        this.chosen.push(ind);
        this.chosen.sort();
      }
    }
  }
}
