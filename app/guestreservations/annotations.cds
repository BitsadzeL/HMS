using GuestService as service from '../../srv/guest-service';

annotate service.Reservations with {
    room @(
        Common.Text            : room.name,
        Common.TextArrangement : #TextFirst
    );
};

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
                Label : 'Room',
                Value : room_ID,
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
            Label : 'Room',
            Value : room_ID,
        },
        {
            $Type : 'UI.DataFieldForAction',
            Action : 'GuestService.cancel',
            Label  : 'Cancel'
        }
    ],

);

