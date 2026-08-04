sap.ui.define([
    "sap/fe/test/JourneyRunner",
	"hms/manager/managerreservations/test/integration/pages/ReservationsList.gen",
	"hms/manager/managerreservations/test/integration/pages/ReservationsObjectPage.gen"
], function (JourneyRunner, ReservationsListGenerated, ReservationsObjectPageGenerated) {
    'use strict';

    const runner = new JourneyRunner({
        launchUrl: sap.ui.require.toUrl('hms/manager/managerreservations') + '/test/flpSandbox.html#hmsmanagermanagerreservations-tile',
        pages: {
			onTheReservationsListGenerated: ReservationsListGenerated,
			onTheReservationsObjectPageGenerated: ReservationsObjectPageGenerated
        },
        async: true
    });

    return runner;
});

