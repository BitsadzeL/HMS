sap.ui.define([
    "sap/fe/test/JourneyRunner",
	"hms/admin/adminrooms/test/integration/pages/RoomsList.gen",
	"hms/admin/adminrooms/test/integration/pages/RoomsObjectPage.gen"
], function (JourneyRunner, RoomsListGenerated, RoomsObjectPageGenerated) {
    'use strict';

    const runner = new JourneyRunner({
        launchUrl: sap.ui.require.toUrl('hms/admin/adminrooms') + '/test/flpSandbox.html#hmsadminadminrooms-tile',
        pages: {
			onTheRoomsListGenerated: RoomsListGenerated,
			onTheRoomsObjectPageGenerated: RoomsObjectPageGenerated
        },
        async: true
    });

    return runner;
});

