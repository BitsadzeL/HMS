sap.ui.define([
    "sap/fe/test/JourneyRunner",
	"hms/managerooms/managerooms/test/integration/pages/RoomsList.gen",
	"hms/managerooms/managerooms/test/integration/pages/RoomsObjectPage.gen"
], function (JourneyRunner, RoomsListGenerated, RoomsObjectPageGenerated) {
    'use strict';

    const runner = new JourneyRunner({
        launchUrl: sap.ui.require.toUrl('hms/managerooms/managerooms') + '/test/flpSandbox.html#hmsmanageroomsmanagerooms-tile',
        pages: {
			onTheRoomsListGenerated: RoomsListGenerated,
			onTheRoomsObjectPageGenerated: RoomsObjectPageGenerated
        },
        async: true
    });

    return runner;
});

