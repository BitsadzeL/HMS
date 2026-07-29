using { hms } from '../db/index';

service AdminService {
  @odata.draft.enabled
  entity Hotels   as projection on hms.Hotels;

  @odata.draft.enabled
  entity Managers as projection on hms.Managers;

  entity Rooms as projection on hms.Rooms;

  @odata.draft.enabled
  entity Guests       as projection on hms.Guests;

  @odata.draft.enabled
  entity Reservations as projection on hms.Reservations;
}

annotate AdminService.Hotels with @(
  Capabilities.NavigationRestrictions: {
    RestrictedProperties: [
      {
        NavigationProperty: managers,
        InsertRestrictions: { Insertable: false },
        UpdateRestrictions: { Updatable: false }
      }
    ]
  }
);

annotate AdminService.Reservations with {
    room @Common.ValueList : {
        $Type : 'Common.ValueListType',
        CollectionPath : 'Rooms',
        Parameters : [
            { $Type : 'Common.ValueListParameterInOut', LocalDataProperty : room_ID, ValueListProperty : 'ID' },
            { $Type : 'Common.ValueListParameterConstant', ValueListProperty : 'IsActiveEntity', Constant : 'true' },
            { $Type : 'Common.ValueListParameterDisplayOnly', ValueListProperty : 'name' },
            { $Type : 'Common.ValueListParameterDisplayOnly', ValueListProperty : 'price_amount' },
            { $Type : 'Common.ValueListParameterDisplayOnly', ValueListProperty : 'price_currency_code' },
        ],
    }
};