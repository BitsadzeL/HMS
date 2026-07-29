using { hms } from '../db/index';

service AdminService {
  @odata.draft.enabled
  entity Hotels   as projection on hms.Hotels;

  @odata.draft.enabled
  entity Managers as projection on hms.Managers;

  @odata.draft.enabled
  entity Rooms as projection on hms.Rooms;

  @odata.draft.enabled
  entity Guests       as projection on hms.Guests;

  @odata.draft.enabled
  entity Reservations as projection on hms.Reservations;
}
