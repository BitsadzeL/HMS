using { hms as db } from '../db';

service GuestService {
  @readonly entity Hotels as projection on db.Hotels;
  @readonly entity Rooms  as projection on db.Rooms;

  @odata.draft.enabled
  entity Guests as projection on db.Guests;

  @odata.draft.enabled
  @restrict: [
    { grant: ['READ','CREATE','UPDATE','cancel'], to: 'guest_access', where: 'exists guest[loginName = $user]' }
  ]
  entity Reservations as projection on db.Reservations actions {
    action cancel();
  };
}