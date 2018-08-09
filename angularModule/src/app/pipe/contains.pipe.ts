import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'contains',
  pure: false
})
export class ContainsPipe implements PipeTransform {

  transform(value: string[], substr?: string): string[] {
    if (substr) {
      const filteredArr = [];
      for (let i = 0; i < value.length; ++i) {
        if (value[i].includes(substr)) {
          filteredArr.push(value[i]);
        }
      }
      return filteredArr;
    } else {
      return value;
    }
  }

}
