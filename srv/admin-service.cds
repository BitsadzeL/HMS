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

  @readonly
  entity ReservationStatuses as projection on hms.ReservationStatuses;
}


annotate AdminService.Reservations with {
  status @(
    Common.ValueListWithFixedValues,
    Common.ValueList : {
      CollectionPath : 'ReservationStatuses',
      Parameters : [
        { $Type: 'Common.ValueListParameterInOut', LocalDataProperty: status, ValueListProperty: 'code' }
      ]
    }
  );
};


annotate AdminService.Hotels with @(
  Capabilities.NavigationRestrictions : {
    RestrictedProperties : [
      {
        NavigationProperty : rooms,
        InsertRestrictions : { Insertable : false }
      }
    ]
  }
);


annotate AdminService.Hotels with @(
  Capabilities.NavigationRestrictions : {
    RestrictedProperties : [
      {
        NavigationProperty : rooms,
        InsertRestrictions : { Insertable : false }
      },
      {
        NavigationProperty : managers,
        InsertRestrictions : { Insertable : false }
      }
    ]
  }
);


annotate AdminService.Rooms with @(
  Capabilities.NavigationRestrictions : {
    RestrictedProperties : [
      {
        NavigationProperty : reservations,
        InsertRestrictions : { Insertable : false }
      }
    ]
  }
);