import { Pipe, PipeTransform } from '@angular/core';
import { NFTCertificate } from '../model/NFTCertificate';

@Pipe({ name: 'cert' })
export class CertPipe implements PipeTransform {
  transform(values: NFTCertificate[], filter: string): NFTCertificate[] {
    if (!filter || filter.length === 0) {
      return values;
    }

    if (values.length === 0) {
      return values;
    }

    return values.filter((value: NFTCertificate) => {
      const tokenFound =
        value.tokenID.toString().indexOf(filter.toLowerCase()) !== -1;
      const firstFound =
        value.firstName.toLowerCase().indexOf(filter.toLowerCase()) !== -1;
      const lastFound =
        value.lastName.toLowerCase().indexOf(filter.toLowerCase()) !== -1;
      const typeFound =
        value.certType.toLowerCase().indexOf(filter.toLowerCase()) !== -1;
      const levelFound =
        value.certLevel.indexOf(filter.toLowerCase()) !== -1;

      if (tokenFound || firstFound || lastFound || typeFound || levelFound) {
        return value;
      }
      return;
    });
  }
}