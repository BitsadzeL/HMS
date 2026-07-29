namespace hms;

using { Currency } from '@sap/cds/common';
using { sap.common.CodeList } from '@sap/cds/common';

type ReservationStatus : String enum {
  active;
  completed;
  cancelled;
}

entity ReservationStatuses : CodeList {
  key code : String enum {
    active;
    completed;
    cancelled;
  };
}



type Price {
  amount   : Decimal(9,2) @assert.range: [0.01,];
  currency : Currency;
}