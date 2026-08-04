using ManagerService as service from '../../srv/manager-service';
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
    ],
);