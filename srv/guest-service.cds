using { hms as db } from '../db';

service GuestService {
  @readonly entity Hotels as projection on db.Hotels;
  @readonly entity Rooms  as projection on db.Rooms;

  entity Guests       as projection on db.Guests;
  entity Reservations as projection on db.Reservations actions{
      action cancel();
    };
}