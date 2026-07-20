namespace hms;

using { cuid, managed } from '@sap/cds/common';

using { hms.Rooms } from './rooms';
using { hms.Guests } from './guests';
using {hms.ReservationStatus} from './types';

entity Reservations : cuid, managed {
  room     : Association to Rooms @mandatory;
  guest    : Association to Guests @mandatory;
  checkIn  : Date @mandatory;
  checkOut : Date @mandatory;
  status   : ReservationStatus default 'active';
}