using { hms } from '../db/index';

service AdminService {
  entity Hotels   as projection on hms.Hotels;
  entity Managers as projection on hms.Managers;
  entity Rooms as projection on hms.Rooms;
}