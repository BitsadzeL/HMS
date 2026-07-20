namespace hms;

using { cuid, managed } from '@sap/cds/common';
using { hms.Reservations } from './reservations';
entity Guests : cuid, managed {
  firstName      : String(60);
  lastName       : String(60);
  personalNumber : String(30) @assert.unique;
  phoneNumber    : String(30) @assert.unique;
  reservations   : Association to many Reservations on reservations.guest = $self;
}