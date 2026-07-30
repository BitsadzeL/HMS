sap.ui.define([
    "sap/fe/test/JourneyRunner",
	"hms/manager/managerrooms/test/integration/pages/RoomsList.gen",
	"hms/manager/managerrooms/test/integration/pages/RoomsObjectPage.gen"
], function (JourneyRunner, RoomsListGenerated, RoomsObjectPageGenerated) {
    'use strict';

    const runner = new JourneyRunner({
        launchUrl: sap.ui.require.toUrl('hms/manager/managerrooms') + '/test/flpSandbox.html#hmsmanagermanagerrooms-tile',
        pages: {
			onTheRoomsListGenerated: RoomsListGenerated,
			onTheRoomsObjectPageGenerated: RoomsObjectPageGenerated
        },
        async: true
    });

    return runner;
});

