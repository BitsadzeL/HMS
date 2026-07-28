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

annotate service.Rooms with {
    hotel @Common.ValueList : {
        $Type : 'Common.ValueListType',
        CollectionPath : 'Hotels',
        Parameters : [
            {
                $Type : 'Common.ValueListParameterInOut',
                LocalDataProperty : hotel_ID,
                ValueListProperty : 'ID',
            },
            {
                $Type : 'Common.ValueListParameterDisplayOnly',
                ValueListProperty : 'name',
            },
            {
                $Type : 'Common.ValueListParameterDisplayOnly',
                ValueListProperty : 'address',
            },
            {
                $Type : 'Common.ValueListParameterDisplayOnly',
                ValueListProperty : 'country',
            },
            {
                $Type : 'Common.ValueListParameterDisplayOnly',
                ValueListProperty : 'city',
            },
        ],
    }
};

