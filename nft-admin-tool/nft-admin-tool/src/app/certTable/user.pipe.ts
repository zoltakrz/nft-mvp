import { Pipe, PipeTransform } from '@angular/core';
import { NFTUser } from '../model/NFTUser';

@Pipe({ name: 'user' })
export class UserPipe implements PipeTransform {
  transform(values: NFTUser[], filter: string): NFTUser[] {
    if (!filter || filter.length === 0) {
      return values;
    }

    if (values.length === 0) {
      return values;
    }

    return values.filter((value: NFTUser) => {

      const emailFound =
        value.email.toLowerCase().indexOf(filter.toLowerCase()) !== -1;
      const firstFound =
        value.firstName.toLowerCase().indexOf(filter.toLowerCase()) !== -1;
      const lastFound =
        value.lastName.toLowerCase().indexOf(filter.toLowerCase()) !== -1;
      const typeFound =
        value.certType.toLowerCase().indexOf(filter.toLowerCase()) !== -1;
      const levelFound =
        value.certLevel.indexOf(filter.toLowerCase()) !== -1;

      if (emailFound || firstFound || lastFound || typeFound || levelFound) {
        return value;
      }
      return;
    });
  }
}