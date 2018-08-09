import {Injectable, Pipe, PipeTransform} from '@angular/core';
import {isFunction} from 'ngx-bootstrap/chronos/utils/type-checks';

@Pipe({
  name: 'object'
})
@Injectable()
export class ObjectPipe implements PipeTransform {

  transform(value: any, args?: any): any {
    // return Object.keys(value);
    const arr = [];
    for (let v in value) {
      if (!isFunction(value[v])) {
        arr.push({
          name: v,
          value: value[v]
        });
      }
    }
    return arr;
  }

}
