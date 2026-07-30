using ManagerService as service from '../../srv/manager-service';
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
    ],
);

