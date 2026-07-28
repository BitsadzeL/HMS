namespace hms;


using { cuid, managed } from '@sap/cds/common';
using { hms.Rooms } from './rooms';
using { hms.Managers } from './managers';

@cds.odata.valuelist
entity Hotels : cuid, managed {
  name     : String(100) @mandatory;
  address  : String(200);
  country  : String(60);
  city     : String(60);
  rating   : Integer @assert.range: [1,5];
  managers : Association to many Managers on managers.hotel = $self;
  rooms    : Composition of many Rooms on rooms.hotel = $self;
}