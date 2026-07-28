using { hms } from '../db/index';

service AdminService {
  @odata.draft.enabled
  entity Hotels   as projection on hms.Hotels;

  @odata.draft.enabled
  entity Managers as projection on hms.Managers;

  entity Rooms as projection on hms.Rooms;
  entity Guests       as projection on hms.Guests;
  entity Reservations as projection on hms.Reservations;
}