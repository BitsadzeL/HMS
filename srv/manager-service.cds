using { hms as db } from '../db';

service ManagerService {
  @readonly
  @restrict: [
    { grant: 'READ', to: 'manager_access', where: 'exists managers[loginName = $user]' }
  ]
  entity Hotels as projection on db.Hotels;

  @odata.draft.enabled
//   @restrict: [
//     { grant: ['READ','CREATE','UPDATE'], to: 'manager_access', where: 'exists hotel.managers[loginName = $user]' }
//   ]
  entity Rooms as projection on db.Rooms;

  @odata.draft.enabled
  entity Guests as projection on db.Guests;

  @odata.draft.enabled
  entity Reservations as projection on db.Reservations actions {
    action cancel();
  };
}