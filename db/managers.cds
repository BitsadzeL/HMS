namespace hms;

using { cuid, managed } from '@sap/cds/common';
using { hms.Hotels } from './hotels';
entity Managers : cuid, managed {
  firstName      : String(60);
  lastName       : String(60);
  email          : String(120) @assert.unique;
  personalNumber : String(30) @assert.unique;
  phoneNumber    : String(30) @assert.unique;
  hotel          : Association to Hotels @mandatory;
}