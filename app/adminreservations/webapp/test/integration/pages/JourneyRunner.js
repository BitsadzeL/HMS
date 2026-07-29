sap.ui.define([
    "sap/fe/test/JourneyRunner",
	"hms/admin/adminreservations/test/integration/pages/ReservationsList.gen",
	"hms/admin/adminreservations/test/integration/pages/ReservationsObjectPage.gen"
], function (JourneyRunner, ReservationsListGenerated, ReservationsObjectPageGenerated) {
    'use strict';

    const runner = new JourneyRunner({
        launchUrl: sap.ui.require.toUrl('hms/admin/adminreservations') + '/test/flpSandbox.html#hmsadminadminreservations-tile',
        pages: {
			onTheReservationsListGenerated: ReservationsListGenerated,
			onTheReservationsObjectPageGenerated: ReservationsObjectPageGenerated
        },
        async: true
    });

    return runner;
});

