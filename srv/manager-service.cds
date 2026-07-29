using { hms as db } from '../db';

service ManagerService {
  @readonly
  entity Hotels as projection on db.Hotels;

  @odata.draft.enabled
  entity Rooms as projection on db.Rooms;

  @readonly
  entity Guests as projection on db.Guests;

  @odata.draft.enabled
  entity Reservations as projection on db.Reservations actions {
    action cancel();
  };
}