using { hms as db } from '../db';

service ManagerService {
  entity Hotels       as projection on db.Hotels;
  entity Rooms        as projection on db.Rooms;
  entity Guests       as projection on db.Guests;
  entity Reservations as projection on db.Reservations;
}