using AdminService as service from '../../srv/admin-service';
annotate service.Reservations with @(
    UI.FieldGroup #GeneratedGroup : {
        $Type : 'UI.FieldGroupType',
        Data : [
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
    ],
);

