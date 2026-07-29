namespace hms;


using { cuid, managed } from '@sap/cds/common';
using { hms.Hotels } from './hotels';
using { hms.Reservations } from './reservations';
using { hms.Price} from './types';

@cds.odata.valuelist
entity Rooms : cuid, managed {
  hotel     : Association to Hotels @mandatory;
  name      : String(50);
  price     : Price;
  available : Boolean default true;
  reservations : Association to many Reservations on reservations.room = $self;
}