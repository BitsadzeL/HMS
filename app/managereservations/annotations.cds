using AdminService as service from '../../srv/admin-service';

annotate service.Reservations with @(
    UI.FieldGroup #GeneratedGroup : {
        $Type : 'UI.FieldGroupType',
        Data : [
            {
                $Type : 'UI.DataField',
                Label : 'Room',
                Value : room_ID,
            },
            {
                $Type : 'UI.DataField',
                Label : 'Guest',
                Value : guest_ID,
            },
            {
                $Type : 'UI.DataField',
                Label : 'checkIn',
                Value : checkIn,
            },
            {
                $Type : 'UI.DataField',
                Label : 'checkOut',
                Value : checkOut,
            },
            {
                $Type : 'UI.DataField',
                Label : 'status',
                Value : status,
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
    ],
    UI.LineItem : [
        {
            $Type : 'UI.DataField',
            Label : 'Room',
            Value : room_ID,
        },
        {
            $Type : 'UI.DataField',
            Label : 'Guest',
            Value : guest_ID,
        },
        {
            $Type : 'UI.DataField',
            Label : 'checkIn',
            Value : checkIn,
        },
        {
            $Type : 'UI.DataField',
            Label : 'checkOut',
            Value : checkOut,
        },
        {
            $Type : 'UI.DataField',
            Label : 'status',
            Value : status,
        },
    ],
);

annotate service.Reservations with {
    room_ID @Common.ValueList : {
        $Type : 'Common.ValueListType',
        CollectionPath : 'Rooms',
        Parameters : [
            {
                $Type : 'Common.ValueListParameterInOut',
                LocalDataProperty : room_ID,
                ValueListProperty : 'ID',
            },
            {
                $Type : 'Common.ValueListParameterDisplayOnly',
                ValueListProperty : 'name',
            },
            {
                $Type : 'Common.ValueListParameterDisplayOnly',
                ValueListProperty : 'price_amount',
            },
            {
                $Type : 'Common.ValueListParameterDisplayOnly',
                ValueListProperty : 'price_currency_code',
            },
            {
                $Type : 'Common.ValueListParameterDisplayOnly',
                ValueListProperty : 'available',
            },
        ],
    }
};

annotate service.Reservations with {
    guest_ID @Common.ValueList : {
        $Type : 'Common.ValueListType',
        CollectionPath : 'Guests',
        Parameters : [
            {
                $Type : 'Common.ValueListParameterInOut',
                LocalDataProperty : guest_ID,
                ValueListProperty : 'ID',
            },
            {
                $Type : 'Common.ValueListParameterDisplayOnly',
                ValueListProperty : 'firstName',
            },
            {
                $Type : 'Common.ValueListParameterDisplayOnly',
                ValueListProperty : 'lastName',
            },
            {
                $Type : 'Common.ValueListParameterDisplayOnly',
                ValueListProperty : 'personalNumber',
            },
            {
                $Type : 'Common.ValueListParameterDisplayOnly',
                ValueListProperty : 'phoneNumber',
            },
        ],
    }
};