using AdminService as service from '../../srv/admin-service';
annotate service.Rooms with @(
    UI.FieldGroup #GeneratedGroup : {
        $Type : 'UI.FieldGroupType',
        Data : [
            {
                $Type : 'UI.DataField',
                Label : 'name',
                Value : name,
            },
            {
                $Type : 'UI.DataField',
                Label : 'price_amount',
                Value : price_amount,
            },
            {
                $Type : 'UI.DataField',
                Label : 'price_currency_code',
                Value : price_currency_code,
            },
            {
                $Type : 'UI.DataField',
                Label : 'available',
                Value : available,
            },
            {
                $Type : 'UI.DataField',
                Label : 'Hotel',
                Value : hotel_ID,
            },
        ],
    },
    UI.Facets : [
        {
            $Type : 'UI.ReferenceFacet',
            ID : 'GeneratedFacet1',
            Label : 'General Information',
            Target : '@UI.FieldGroup#GeneratedGroup',
        },
        {
          $Type : 'UI.ReferenceFacet',
          ID    : 'ReservationsFacet',
          Label : 'Reservations',
          Target: 'reservations/@UI.LineItem',
        },
    ],
    UI.LineItem : [
        {
            $Type : 'UI.DataField',
            Label : 'name',
            Value : name,
        },
        {
            $Type : 'UI.DataField',
            Label : 'price_amount',
            Value : price_amount,
        },
        {
            $Type : 'UI.DataField',
            Label : 'price_currency_code',
            Value : price_currency_code,
        },
        {
            $Type : 'UI.DataField',
            Label : 'available',
            Value : available,
        },
        {
            $Type : 'UI.DataField',
            Label : 'Hotel',
            Value : hotel_ID,
        },
    ],
);